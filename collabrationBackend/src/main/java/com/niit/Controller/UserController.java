package com.niit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.DAO.UserDAO;
import com.niit.Model.UserModel;

@RestController
public class UserController {

	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	UserModel userModel;
	
	public ResponseEntity<List<UserModel>> getAllUser(){  //ResponseEntity constructor, if we pass java object, it returns json object
		List userobjlist= userDAO.list();        //need to convert into json objects
	
		if(userobjlist.isEmpty()){
		userModel.setErrorCode("100");
		userModel.setErrorMessage("Not user are available");
		userobjlist.add(userModel);
		return new ResponseEntity <List<UserModel>>(userobjlist,HttpStatus.OK);
		}
		userModel.setErrorCode("200");
		userModel.setErrorMessage("User is available");
			return new ResponseEntity <List<UserModel>>(userobjlist,HttpStatus.OK);
		}
	
	@GetMapping("/validate/{userid}/{password}")
	public ResponseEntity<UserModel> validateCredentials(@PathVariable("userid")String userid, @PathVariable("password") String password){
		
		if(userDAO.validate(userid, password)==null){
			userModel=new UserModel();
			userModel.setErrorCode("404");
			userModel.setErrorMessage("Invalid Credential..password..plese try again");
		
		}else{
			userModel.setErrorCode("200");
			userModel.setErrorMessage("You aer succesfully logged in ....");
		}
return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);
}
	

	
	
	
	@PostMapping(value="/register")
	public ResponseEntity<UserModel> Register(@RequestBody UserModel userModel){
		if(userDAO.get(userModel.getUsername())==null){
			userDAO.save(userModel);
			userModel.setErrorCode("200");
			userModel.setErrorMessage("Successfully registered");}
		else{userModel.setErrorCode("400");
		userModel.setErrorMessage("User Exist with name "+userModel.getUsername());
		}
			return new ResponseEntity<UserModel>(userModel,HttpStatus.OK);
		
}
	@GetMapping("/hello")
	public String rajesh(){
		return "hwllo rajesh, how u doing";
	}
	
	@GetMapping("/getUser/{username}")
	public ResponseEntity<UserModel> getUser(@PathVariable("username") String username){
		userModel=userDAO.get(username);
		if(userModel==null){
			userModel= new UserModel();
			userModel.setErrorCode("404");
			userModel.setErrorMessage("No user found for "+username);
		}
	return new ResponseEntity<UserModel>(userModel,HttpStatus.OK);  
			}
	
}













