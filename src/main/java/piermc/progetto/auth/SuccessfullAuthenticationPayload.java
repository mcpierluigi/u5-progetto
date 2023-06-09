package piermc.progetto.auth;

import lombok.*;

@Data
@AllArgsConstructor
public class SuccessfullAuthenticationPayload {
	private String accessToken;
}
