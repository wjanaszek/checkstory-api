package org.wjanaszek.checkstory.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;
import org.wjanaszek.checkstory.domain.Photo;
import org.wjanaszek.checkstory.domain.PhotoWithContent;
import org.wjanaszek.checkstory.domain.Story;
import org.wjanaszek.checkstory.exception.BadRequestException;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.repository.PhotoRepository;
import org.wjanaszek.checkstory.repository.StoryRepository;
import org.wjanaszek.checkstory.request.CreateUpdatePhotoRequest;
import org.wjanaszek.checkstory.service.PhotoService;
import org.wjanaszek.checkstory.utils.TimeProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private TimeProvider timeProvider;

    private DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    private int targetSize = 400;

    @Override
    public PhotoWithContent createPhoto(Story story, CreateUpdatePhotoRequest createPhotoRequest) throws NoResourceFoundException {
        Photo photo = new Photo();
        PhotoWithContent photoWithContent = null;
        photo.setCreateDate(createPhotoRequest.getCreateDate());
        photo.setImageType(createPhotoRequest.getImageType());
        photo.setOriginalPhoto(createPhotoRequest.getOriginalPhoto().charAt(0));
        if (story != null) {
            photo.setStory(story);

            String path = environment.getProperty("uploadsPath").toString();
            path += story.getId().toString() + "/photos/" + UUID.randomUUID();
            path += "." + createPhotoRequest.getImageType();
            photo.setPathToFile(path);

            saveImage(path, createPhotoRequest.getContent());

            photoRepository.save(photo);
            Set<Photo> photos = story.getPhotos();
            photos.add(photo);
            story.setPhotos(photos);
            storyRepository.save(story);

            photoWithContent = new PhotoWithContent();
            photoWithContent.setId(photo.getId());
            photoWithContent.setContent(generateBase64Thumbnail(loadFile(photo.getPathToFile())));
            photoWithContent.setImageType(photo.getImageType());
            photoWithContent.setCreateDate(photo.getCreateDate());
        } else {
            throw new NoResourceFoundException();
        }
        return photoWithContent;
    }

    public String getBase64EncodedImage(String path) {
        File file = loadFile(path);
        String encode = null;
        if (file != null) {
            try {
                encode = Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encode;
    }

    public String getBase64EncodedImage(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }

    public List<PhotoWithContent> getPhotosWithContentByStoryId(Long id) {
        return photoRepository.getPhotosByStoryId(id)
                .stream()
                .map(photo -> {
                    PhotoWithContent photoWithContent = new PhotoWithContent();
                    photoWithContent.setId(photo.getId());
                    photoWithContent.setStoryId(id);
                    photoWithContent.setCreateDate(photo.getCreateDate());
                    photoWithContent.setImageType(photo.getImageType());
                    photoWithContent.setContent(generateBase64Thumbnail(loadFile(photo.getPathToFile())));
                    return photoWithContent;
                })
                .collect(Collectors.toList());
    }

    public Photo findOne(Long id) {
        return photoRepository.findOne(id);
    }

    public PhotoWithContent updatePhoto(
            Story story,
            Long photoId,
            CreateUpdatePhotoRequest updatePhotoRequest
    ) throws BadRequestException, NoResourceFoundException {
        Photo photo = photoRepository.findOne(photoId);
        PhotoWithContent photoWithContent = null;
        if (photo != null) {
            if (updatePhotoRequest.getContent() != null) {
                saveImage(photo.getPathToFile(), updatePhotoRequest.getContent());
            }
            if (updatePhotoRequest.getOriginalPhoto() != null) {
                photo.setOriginalPhoto(updatePhotoRequest.getOriginalPhoto().charAt(0));
            }
            if (updatePhotoRequest.getImageType() != null) {
                photo.setImageType(updatePhotoRequest.getImageType());
            }
            try {
                photo.setUpdateDate(format.parse(timeProvider.now().toString()));
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
            photoRepository.save(photo);

            story.setPhotos(story.getPhotos()
                    .stream()
                    .map(photo1 -> photo1.getId().equals(photo.getId()) ? photo : photo1)
                    .collect(Collectors.toSet()));
            storyRepository.save(story);

            photoWithContent = new PhotoWithContent();
            photoWithContent.setContent(updatePhotoRequest.getContent());
            photoWithContent.setOriginalPhoto(updatePhotoRequest.getOriginalPhoto().charAt(0));
            photoWithContent.setCreateDate(updatePhotoRequest.getCreateDate());
            photoWithContent.setImageType(updatePhotoRequest.getImageType());
        } else {
            throw new NoResourceFoundException();
        }
        return photoWithContent;
    }

    public void removePhoto(Story story, Long photoId) throws NoResourceFoundException {
        Photo photo = photoRepository.findOne(photoId);
        if (photo == null) {
            throw new NoResourceFoundException();
        }
        photoRepository.delete(photoId);
        story.setPhotos(story.getPhotos()
                .stream()
                .filter(photo1 -> !photo1.getId().equals(photo.getId()))
                .collect(Collectors.toSet()));
        storyRepository.save(story);
    }

    public String generateBase64Thumbnail(File file) {
        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage scaledImage = Scalr.resize(srcImage, targetSize); // Scale image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(scaledImage, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBase64EncodedImage(baos.toByteArray());
    }

    public String generateBase64Thumbnail(String base64String, Integer targetSize) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = Base64.getMimeDecoder().decode(base64String);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        image = Scalr.resize(image, targetSize);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBase64EncodedImage(baos.toByteArray());
    }

    private File loadFile(String path) {
        File file = null;
        try {
            file = ResourceUtils.getFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void saveImage(String path, String base64Content) {
        try {
            String dirPath = "";
            String[] pathParts = path.split("/");
            for (int i = 0; i < pathParts.length - 1; i++) {
                dirPath += pathParts[i] + "/";
            }

            // create directories if not exist
            new File(dirPath).mkdirs();

            FileOutputStream imageOutFile = new FileOutputStream(path);
            byte[] imageByteArray = Base64.getMimeDecoder().decode(base64Content);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
