package br.com.ancoraweb.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

public class ArquivoUtil {

	public static byte [] compactaXml(String xml) throws IOException{
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
		try (OutputStream outputStream = new GZIPOutputStream(obj)){
			outputStream.write(xml.getBytes(StandardCharsets.UTF_8));
		}
		return obj.toByteArray();
	}
	
	
	public static String descompactaXml(byte[] gZip) throws IOException{
		try (final GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(gZip))){
			final StringWriter stringWriter = new StringWriter();
			IOUtils.copy(gzipInputStream, stringWriter);
			return stringWriter.toString();
		}
	}
}
