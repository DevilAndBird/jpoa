package com.fh.resolver;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

public class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {

	 
	@Override
	public Integer deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context)
			throws com.google.gson.JsonParseException {
		  try{
			   if(json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为int类型,如果后台返回""或者null,则返回0
			    return null;
			   }
			  }catch (Exception ignore) {
			  }
			  try{
			   return json.getAsInt();
			  }catch (NumberFormatException e) {
			   throw new JsonSyntaxException(e);
			  }
	}

	@Override
	public JsonElement serialize(Integer src, Type typeOfSrc,
			JsonSerializationContext context) {
		 return new JsonPrimitive(src);
	}

	
}
