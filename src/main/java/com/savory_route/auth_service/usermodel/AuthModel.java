package com.savory_route.auth_service.usermodel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Document("users")
public class AuthModel {
	
	@Id
	private String id;
	private String email;
	
	@JsonIgnore
	private String password;
	private String firstName;
	private String lastName;
	private long phoneNumber;

	public AuthModel(String id, String email, String password, String firstName, String lastName, long phoneNumber) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}
	public AuthModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + "]";
	}
	
	
	public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private long phoneNumber;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder phoneNumber(long phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public AuthModel build() {
            return new AuthModel(id, email, password, firstName, lastName, phoneNumber);
        }
    }
}
