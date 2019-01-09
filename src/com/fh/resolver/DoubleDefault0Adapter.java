package com.fh.resolver;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

public class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double>{

	@Override
	public Double deserialize(JsonElement json, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		try {
			if(json.getAsString().equals("") || json.getAsString().equals("")){
				return null;
			}
		} catch (Exception e1) {
		}
		
		try {
			return json.getAsDouble();
		} catch (NumberFormatException  e) {
			   throw new JsonSyntaxException(e);
		}
	}

	@Override
	public JsonElement serialize(Double arg0, Type arg1,
			JsonSerializationContext arg2) {
		return new JsonPrimitive(arg0);
	}

}
