package com.niit.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.DAO.BlogDAO;
import com.niit.Model.BlogModel;

@RestController
public class BlogController {

	@Autowired
	private BlogModel blogModel;
	@Autowired
	private BlogDAO blogDAO;
	
	@GetMapping("/getallBlog")
	public List<BlogModel> getallBlog(){
		return blogDAO.getAllBlog();
	}
	
	@PostMapping(value = "/blog")
	public ResponseEntity<BlogModel> createBlog(@RequestBody BlogModel blogmodel, HttpSession session) {
		
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		blogmodel.setUserid(loggedInUserID);
		blogmodel.setBlogstatus('N');// A->Accepted,  R->Rejected
		
		blogDAO.saveblog(blogmodel);

		return new ResponseEntity<BlogModel>(blogmodel, HttpStatus.OK);
	}
	
	@PutMapping("/approveblog/{blogID}")
	public BlogModel approveblog(@PathVariable("blogid")int blogid){
		blogModel=blogDAO.getBlog(blogid);
		blogModel.setBlogstatus('A');
		
		if(blogDAO.update(blogModel)){
			blogModel.setErrorCode("200");
			blogModel.setErrorMessage("SuccessFully approved");
		}else{
			blogModel.setErrorCode("404");
			blogModel.setErrorMessage("No able to approve the blog");
			
		}return blogModel;
	}
}
