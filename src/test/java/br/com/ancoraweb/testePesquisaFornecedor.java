package br.com.ancoraweb;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class testePesquisaFornecedor {
	
	   private static String id_contato_con = null;

	public static void main(String[] args) throws IOException {
		   

				 OkHttpClient client = new OkHttpClient().newBuilder()
						  .build();
						Request request = new Request.Builder()
						  .url("https://api.superlogica.net/v2/condor/fornecedores/index/?contatosDoTipo=fornecedores&pesquisa=818264610001109")
						  .addHeader("Content-Type", "application/json")
						  .addHeader("app_token", "b9fbdf19-2b08-4295-ac73-3404eb7e2fe4")
						  .addHeader("access_token", "7fadf2e3-4bde-4f1b-bd8b-8143e19f273f")
						  .build();
						Response response = client.newCall(request).execute();				 
						System.out.println(response);
						String jsonData = response.body().string();
						System.out.println(jsonData);
						JSONArray jsonarray;
						try {
							jsonarray = new JSONArray(jsonData);
							for(int i=0; i < jsonarray.length(); i++) {
								JSONObject jsonobject = jsonarray.getJSONObject(i);
								id_contato_con  = jsonobject.getString("id_contato_con");
								System.out.println("id_contato_con = " + id_contato_con);
							}
							if(id_contato_con == null || id_contato_con.isEmpty()) {
								cadastraFavorecido();
							}
						}catch (JSONException e) {
							e.printStackTrace();
						 }
						
						
	   }

	private static void cadastraFavorecido() throws JSONException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				MediaType mediaType = MediaType.parse("text/plain");
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
				  .url("https://api.superlogica.net/v2/condor/fornecedores/?"
				  		+ "ID_CONDOMINIO_COND=2"
				  		+ "&ID_CONTATO_CON="
				  		+ "&ST_CPF_CON=81826461000110"
				  		+ "&ST_NOME_CON=TESTE ANCORA BDELFES3"
				  		+ "&ST_FANTASIA_CON=TESTE ANCORA BDELFES3"
				  		+ "&ST_INSCRICAOMUNICIPAL_CON=12345678"
				  		+ "&ST_RG_CON="
				  		+ "&ST_ORGAOEMISSOR_CON="
				  		+ "&ST_TELEFONE_CON="
				  		+ "&ST_FAX_CON="
				  		+ "&ST_BAIRRO_CON="
				  		+ "&ST_EMAIL_CON="
				  		+ "&ST_CEP_CON="
				  		+ "&ST_ENDERECO_CON="
				  		+ "&ST_NUMEROENDERECO_CON="
				  		+ "&ST_CIDADE_CON="
				  		+ "&ST_ESTADO_CON="
				  		+ "&ST_COMPLEMENTO_CON="
				  		+ "&ST_INSS_CON="
				  		+ "&NM_RPASEQUENCIAL_CON="
				  		+ "&ID_TIPOCONTATO_TCON=0"
				  		+ "&ID_FORMA_PAG="
				  		+ "&ST_AGENCIA_CON="
				  		+ "&ST_BANCO_CON="
				  		+ "&ST_CONTABANCARIA_CON="
				  		+ "&FL_TIPOCONTA_CON="
				  		+ "&ST_OPERACAO_CON="
				  		+ "&CONTATO_IMPOSTO[0][ID_RV2_COD_IMP]="
				  		+ "&CONTATO_IMPOSTO[0][ST_RV2_NOME_IMP]="
				  		+ "&CONTATO_IMPOSTO[0][VALOR_ALIQUOTA]=0"
				  		+ "&CONTATO_IMPOSTO[0][NM_RV2_ALIQUOTA_CIMP]="
				  		+ "&FAVORECIDO_PIX[0][FL_TIPOCHAVE_PIX]="
				  		+ "&FAVORECIDO_PIX[0][ST_CHAVE_PIX]=")
				  .method("POST", body)
				  .addHeader("app_token", "b9fbdf19-2b08-4295-ac73-3404eb7e2fe4")
				  .addHeader("access_token", "7fadf2e3-4bde-4f1b-bd8b-8143e19f273f")
				  .build();
				try {
					Response response = client.newCall(request).execute();
					System.out.println(response);
					String jsonData = response.body().string();
					System.out.println(jsonData);
					JSONArray jsonarray;
					jsonarray = new JSONArray(jsonData);
					for(int i=0; i < jsonarray.length(); i++) {
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						id_contato_con = jsonobject.getString("data");
						JSONObject idjAVA = new JSONObject(id_contato_con);
						id_contato_con = idjAVA.getString("id_contato_con");
						System.out.println("id_contato_con = " + id_contato_con);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
		
	}
}