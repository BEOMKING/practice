package jpabook.jpashop.service;

import jpabook.jpashop.domain.DBFile;
import jpabook.jpashop.exception.FileStorageException;
import jpabook.jpashop.exception.MyFileNotFoundException;
import jpabook.jpashop.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    @Transactional
    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            dbFileRepository.save(dbFile);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

            dbFile.setDownload_uri(fileDownloadUri);

            return dbFile;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
            .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

//    @Transactional
//    public DBFile makeDownloadUri(DBFile dbFile){
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//            .path("/downloadFile/")
//            .path(dbFile.getId())
//            .toUriString();
//
//        dbFile.setDownload_uri(fileDownloadUri);
//        return dbFile;
//    }
}
