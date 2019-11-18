package org.mdmi.rt.service.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mdmi.core.Mdmi;
import org.mdmi.core.MdmiTransferInfo;
import org.mdmi.core.engine.MdmiUow;
import org.mdmi.core.runtime.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.jersey.multipart.FormDataParam;

@RestController
public class MdmiEngine {

	@javax.ws.rs.core.Context
	ServletContext context;

	static Map<String, ArrayList<String>> organizationSourceMaps = new TreeMap<String, ArrayList<String>>(
		String.CASE_INSENSITIVE_ORDER);

	static Map<String, ArrayList<String>> organizationTargetMaps = new TreeMap<String, ArrayList<String>>(
		String.CASE_INSENSITIVE_ORDER);

	static Boolean loaded = Boolean.FALSE;

	private static Logger logger = LoggerFactory.getLogger(MdmiEngine.class);

	private void loadMaps() throws IOException {

		synchronized (loaded) {

			if (loaded) {
				return;
			}

			Set<String> maps = listFilesUsingJavaIO("/maps");

			for (String map : maps) {
				InputStream targetStream = new FileInputStream("/maps/" + map);
				Mdmi.INSTANCE().getResolver().resolve(targetStream);
			}

			loaded = Boolean.TRUE;

		}
	}

	/**
	 * @param string
	 * @return
	 */
	private Set<String> listFilesUsingJavaIO(String dir) {
		return Stream.of(new File(dir).listFiles()).filter(
			file -> (!file.isDirectory() && file.toString().endsWith("mdmi"))).map(File::getName).collect(
				Collectors.toSet());
	}

	private String stackTrace(Exception cause) {
		if (cause == null) {
			return "";
		}
		StringWriter sw = new StringWriter(1024);
		final PrintWriter pw = new PrintWriter(sw);
		cause.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}

	@RequestMapping("/")
	public String get(HttpServletRequest req, @RequestParam("organization") Optional<String> organization,
			@RequestParam("list") Optional<String> list) throws Exception {

		String from = _getUserFrom(req);
		MDC.put("client", from);

		UUID transactionID = java.util.UUID.randomUUID();

		MDC.put("transactionId", transactionID.toString());

		loadMaps();

		ArrayList<String> filter = null;

		return Mdmi.INSTANCE().getResolver().getActiveMaps(filter);
	}

	private static byte[] read(InputStream input) throws IOException {
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
			return buffer.lines().collect(Collectors.joining("\n")).getBytes();
		}
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response transformation(@Context HttpServletRequest req, @FormDataParam("source") String source,
			@FormDataParam("target") String target, @FormDataParam("message") InputStream uploadedInputStream) {

		logger.debug("DEBUG Start transformation ");
		try {

			loadMaps();

			HashMap<String, String> sourceProperties = new HashMap<String, String>();

			HashMap<String, String> targetProperties = new HashMap<String, String>();

			MdmiUow.setSerializeSemanticModel(false);

			final StaticLoggerBinder binder = StaticLoggerBinder.getSingleton();
			logger.info(binder.getLoggerFactory().getClass().getCanonicalName());
			logger.info(binder.getLoggerFactoryClassStr());

			String result = RuntimeService.runTransformation(
				source, read(uploadedInputStream), target, null, sourceProperties, targetProperties);
			return Response.ok(result).build();
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			logger.error(stackTrace(ex));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type("text/html").entity(
				ex.getMessage()).build();
		}
	}

	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	public static final String MYSQL_URL = "jdbc:mysql://mdixdb.c0b4at8xfg9n.us-east-1.rds.amazonaws.com/testdata?" +
			"user=MDIXMySql&password=SQL3q20!9Today";

	private String _getUserFrom(HttpServletRequest req) {
		String xForwardedFor = req.getHeader("X-Forwarded-For");
		xForwardedFor = xForwardedFor != null && xForwardedFor.contains(",")
				? xForwardedFor.split(",")[0]
				: xForwardedFor;
		String remoteHost = req.getRemoteHost();
		String remoteAddr = req.getRemoteAddr();
		int remotePort = req.getRemotePort();
		StringBuffer sb = new StringBuffer();
		if (remoteHost != null && !"".equals(remoteHost) && !remoteHost.equals(remoteAddr)) {
			sb.append(remoteHost).append(" ");
		}
		if (xForwardedFor != null && !"".equals(xForwardedFor)) {
			sb.append(xForwardedFor).append("(fwd)=>");
		}
		if (remoteAddr != null || !"".equals(remoteAddr)) {
			sb.append(remoteAddr).append(":").append(remotePort);
		}
		return sb.toString();
	}

	static class MTI {
		public MdmiTransferInfo ti;

		public String error;

		public MTI() {
		}

		public MTI(String err) {
			ti = null;
			error = err;
		}
	}

	@RequestMapping(value = "/transformation")
	String getTransformation() throws IOException {
		ArrayList<String> filter = null;
		loadMaps();
		return Mdmi.INSTANCE().getResolver().getActiveMaps(filter);
	}

}
