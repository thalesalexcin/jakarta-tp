package org.eclipse.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
@Builder
@ToString(exclude = {"password"})
public class User {
	@NonNull
	Integer id;
	@NonNull
	String firstName;
	@NonNull
	String lastName;
	@NonNull
	String email;
	
	String password;
}
