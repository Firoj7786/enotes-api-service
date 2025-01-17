package com.becoder.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.becoder.dto.SpecificResponssDto;
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
	public ResponseEntity<?> downloadFile(@PathVariable  int id) throws Exception {
		  FileDetails fileDetails = notesService.getFileDetails(id);
		  // Download the file as a byte array
	        byte[] data = notesService.downloadFile(fileDetails);
	        
//	        if (data == null || data.length == 0) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//	                .body("File data is not available for ID: " + id);
//	        }
//	        // Return the file as a response
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//	        headers.setContentDisposition(ContentDisposition.builder("attachment")
//	            .filename(fileDetails.getDisplayFileName())
//	            .build());
//	        return ResponseEntity.ok().headers(headers).body(data);
			HttpHeaders headers = new HttpHeaders();
			String contentType = CommonUtils.getContentType(fileDetails.getOriginalFileName());
			headers.setContentType(MediaType.parseMediaType(contentType));
			headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

			return ResponseEntity.ok().headers(headers).body(data);
	        
            }
}
