package com.becoder.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.SpecificResponssDto;
import com.becoder.model.FileDetails;

public interface NotesService {
	 
 	 public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
 	 public List<NotesDto> getAllNotes();

	 public FileDetails getFileDetails(Integer id) throws Exception;

	 public byte[] downloadFile(FileDetails fileDetails) throws IOException;
}
