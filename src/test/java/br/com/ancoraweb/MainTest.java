package br.com.ancoraweb;

import java.io.File;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import okhttp3.Response;


public class MainTest {
	
	
    public static void main(String[] args) throws IOException, JSONException {

    	OkHttpClient client = new OkHttpClient().newBuilder().build();
    			File file = new File("E:\\projetos\\ANCORA\\notas\\nfe.pdf");
				RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
    			  .addFormDataPart("ID_RESPONSAVEL_ARQ","2")
    			  .addFormDataPart("FL_TIPO_ARQ","9")
    			  .addFormDataPart("ARQUIVO",file.getName() ,RequestBody.create(MediaType.parse("application/octet-stream"), file)).build();
    			Request request = new Request.Builder()
    			  .url("https://api.superlogica.net/v2/condor/arquivos/")
    			  .method("POST", body)
    			  .addHeader("Content-Type", "application/x-www-form-urlencoded")
    			  .addHeader("app_token", "b9fbdf19-2b08-4295-ac73-3404eb7e2fe4")
    			  .addHeader("access_token", "7fadf2e3-4bde-4f1b-bd8b-8143e19f273f")
    			  .build();
    			
    			Response response = client.newCall(request).execute();
    			String jsonData = response.body().string();
    			JSONArray jsonarray = new JSONArray(jsonData);
    			String id = null;
    			for(int i=0; i < jsonarray.length(); i++) {
    			    JSONObject jsonobject = jsonarray.getJSONObject(i);
    			    id = jsonobject.getString("data");
    			}
    			JSONObject idjAVA = new JSONObject(id);
    			id = idjAVA.getString("id_arquivo_arq");
    			
    			
    			System.out.println(id);
    			
    			
    }
    
  
}
