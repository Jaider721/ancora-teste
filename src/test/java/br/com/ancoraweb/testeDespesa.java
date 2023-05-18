package br.com.ancoraweb;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import okhttp3.Response;

public class testeDespesa {
	
    public static void main(String[] args) throws IOException {
	
	
	OkHttpClient client = new OkHttpClient().newBuilder()
			  .build();
			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
			RequestBody body = RequestBody.create(mediaType, "ID_CONDOMINIO_COND=2&ST_NOME_CON=Favorecido&ID_CONTATO_CON=131001&DT_VENCIMENTOPRIMEIRAPARCELA=01/06/2023&ID_FORMA_PAG=0&FL_MARCAR_PARA_REMESSA=0&CHECK_LIQUIDAR_TODOS_CH=&MARCAR_PARA_IMPRESSAO=0&APROPRIACAO[0][ST_CONTA_CONT]=2.999999&APROPRIACAO[0][ST_DESCRICAO_CONT]=2.999999 - Não categorizado&APROPRIACAO[0][ID_DESPESA_DES]=&APROPRIACAO[0][ST_COMPLEMENTO_APRO]=&APROPRIACAO[0][VL_VALOR_PDES]=141,85&DT_LIQUIDACAO_PDES=&VL_DESCONTO_PDES=0&VL_MULTA_PDES=0&VL_JUROS_PDES=0&VL_PAGO=0&FL_ACAO_IMPRESSAO=1&NM_NUMERO_CH=&RETENCOES[0][DT_VENCIMENTO_PDES]=&RETENCOES[0][ID_DESPESA_DES]=0&RETENCOES[0][FL_LIQUIDADO_PDES]=&RETENCOES[0][ID_PARCELA_PDES]=&RETENCOES[0][ID_RV2_IMPOSTO_DES]=&RETENCOES[0][VL_RV2_VALORRETIDO_DES]=&RETENCOES[0][ST_COMPLEMENTO_PDES]=&RETENCOES[0][VL_RV2_SUBEMPREITADA_DES]=&RETENCOES[0][VL_RV2_MATERIAL_DES]=&RETENCOES[0][VL_RV2_MAODEOBRA_DES]=&RETENCOES[0][BASE_CALCULO]=0&RETENCOES[0][ST_CODIGOBARRAS_PDES]=&ID_CONTABANCO_CB=55&DT_DESPESA_DES=&ST_DOCUMENTO_DES=&ID_TIPO_DOC=1&ST_SERIENOTA_DES=132413&ARQUIVOS[0][ID_ARQUIVO_ARQ]=79879");
			Request request = new Request.Builder()
			  .url("https://api.superlogica.net/v2/condor/despesas/")
			  .method("POST", body)
			  .addHeader("Content-Type", "application/x-www-form-urlencoded")
			  .addHeader("app_token", "b9fbdf19-2b08-4295-ac73-3404eb7e2fe4")
			  .addHeader("access_token", "7fadf2e3-4bde-4f1b-bd8b-8143e19f273f")
			  .build();
			Response response = client.newCall(request).execute();
			System.out.println(response);
			
    }

}
