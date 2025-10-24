package org.eclipse.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Listing {
	@NonNull
	Integer id;
	@NonNull
	String title;
	String description;
	@NonNull
	Double price;
	String city;
	@NonNull
	Integer ownerId;
}
