package com.becoder.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.FavouriteNoteDto;
import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.dto.SpecificResponssDto;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.model.FileDetails;

public interface NotesService {
	 
 	 public Boolean saveNotes(String notes,MultipartFile file) throws Exception;
	
 	 public List<NotesDto> getAllNotes();

	 public FileDetails getFileDetails(Integer id) throws Exception;

	 public byte[] downloadFile(FileDetails fileDetails) throws IOException;

	 public NotesResponse getUserNotes(int userId ,int pageNo, int pageSize);

	 public void softDeleteNotes(Integer id) throws Exception;

	 public void restoreDeletedNotes(int userId) throws ResourceNotFoundException;

	 public List<NotesDto> getUserRecycleBinNotes(int userId);

	 public void hardDeleteNotes(Integer id) throws Exception;

	 public void emptyRecycleBin(int userId);

	 public void favouriteNotes(int NoteId) throws ResourceNotFoundException;

	 public void unFavoriteNotes(Integer favouriteNoteId) throws Exception;

	 public List<FavouriteNoteDto> getUserFavoriteNotes() throws Exception;

	 public boolean copyNotes(Integer id) throws ResourceNotFoundException;


}
