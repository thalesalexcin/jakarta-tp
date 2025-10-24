package org.eclipse.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Favorite {
	@NonNull
	Integer id;
	@NonNull
	Integer listingId;
	@NonNull
	Integer userId;
}
