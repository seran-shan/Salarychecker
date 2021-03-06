package salarychecker.json.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import salarychecker.core.AbstractUser;
import salarychecker.core.Accounts;

/**
 * Class to serialize {@link Accounts}.
 */
public class AccountsSerializer extends JsonSerializer<Accounts> {

  /*
   * format: { "Accounts": [ ... ] }
   */

  @Override
  public void serialize(Accounts accounts, JsonGenerator jsonGen,
      SerializerProvider serializerProvider) throws IOException {
    jsonGen.writeStartObject();

    if (accounts.getAccounts() != null) {
      jsonGen.writeArrayFieldStart("Accounts");
      for (AbstractUser user : accounts.getAccounts()) {
        jsonGen.writeObject(user);
      }
      jsonGen.writeEndArray();
    }
    jsonGen.writeEndObject();
  }
}

