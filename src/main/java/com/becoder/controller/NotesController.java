package com.becoder.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.dto.NotesDto;
import com.becoder.dto.NotesResponse;
import com.becoder.dto.SpecificResponssDto;
import com.becoder.exception.ResourceNotFoundException;
import com.becoder.service.NotesService;
import com.becoder.model.FileDetails;
import com.becoder.service.impl.NotesServiceImpl;
import com.becoder.util.CommonUtils;

@RestController
@RequestMapping("api/v1/notes")
public class NotesController {
	@Autowired
	private NotesService notesService;

	@PostMapping("/")
	// CREATE AND UPDATE BOTH HANDLED IN THIS API
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file)
			throws Exception {

		Boolean saveNotes = notesService.saveNotes(notes, file);
		if (saveNotes) {
			return CommonUtils.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}
		return CommonUtils.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/")
	public ResponseEntity<?> getListNotes() {
		List<NotesDto> list = notesService.getAllNotes();
		if (!list.isEmpty()) {
			return CommonUtils.createBuildResponse(list, HttpStatus.OK);
		}
		return CommonUtils.createErrorResponseMessage("List is not available", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable int id) throws Exception {
		FileDetails fileDetails = notesService.getFileDetails(id);
		// Download the file as a byte array
		byte[] data = notesService.downloadFile(fileDetails);
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtils.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

		return ResponseEntity.ok().headers(headers).body(data);

	}

	@GetMapping("/userNotes/{userId}")
	public ResponseEntity<?> getAllNotesByUser(@PathVariable int userId,
			@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		NotesResponse notes = notesService.getUserNotes(userId, pageNo, pageSize);
//		if (CollectionUtils.isEmpty(notes)) {
//		return ResponseEntity.noContent().build();
//	}
		return CommonUtils.createBuildResponse(notes, HttpStatus.OK);
	}

	@GetMapping("/delete/{userId}")
	public ResponseEntity<?> deleteNotes(@PathVariable int userId) throws Exception {
		notesService.softDeleteNotes(userId);
		return CommonUtils.createBuildResponseMessage("Notes Deleted Successfully", HttpStatus.OK);

	}

	@GetMapping("/restore/{userId}")
	public ResponseEntity<?> restoreNotes(@PathVariable int userId) throws ResourceNotFoundException {
		notesService.restoreDeletedNotes(userId);
		return CommonUtils.createBuildResponseMessage("Notes restored Successfully", HttpStatus.OK);
	}

	@GetMapping("/recycle-bin/{userId}")
	public ResponseEntity<?> getUserRecycleBinNotes(@PathVariable int userId) {
		List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);
		if (!notes.isEmpty()) {
			return CommonUtils.createBuildResponse(notes, HttpStatus.OK);
		}
		return CommonUtils.createErrorResponseMessage("Recycle-Bin is empty", HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/hardDelete/{userId}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable int userId) throws Exception {
		notesService.hardDeleteNotes(userId);
		return CommonUtils.createBuildResponseMessage("Notes Deleted Successfully", HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> emptyRecyleBin() throws Exception {
		int userId = 2;
		notesService.emptyRecycleBin(userId);
		return CommonUtils.createBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
}
