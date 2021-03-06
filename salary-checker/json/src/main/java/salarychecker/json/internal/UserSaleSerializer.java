package salarychecker.json.internal;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import salarychecker.core.UserSale;

/**
 * Class to serialize {@link UserSale}.
 */
public class UserSaleSerializer extends JsonSerializer<UserSale> {

  /*
  format:
  {
    "firstname": "...",
    "lastname": "...", 
    "email": "...",
    "password": "...",
    "socialNumber": "...",
    "employeeNyumber": "...",
    "employerEmail": "...",
    "taxCount": "...",
    "userSale": [...]
  }
  */

  @Override
  public void serialize(UserSale userSale, JsonGenerator jsonGen,
                        SerializerProvider serializerProvider)
      throws IOException {
    jsonGen.writeStartObject();

    jsonGen.writeStringField("salesperiod", userSale.getSalesperiod());
    jsonGen.writeNumberField("expected", userSale.getExpected());
    jsonGen.writeNumberField("paid", userSale.getPaid());
    jsonGen.writeNumberField("difference", userSale.getDifference());

    jsonGen.writeEndObject();
  }
}