package fr.smile.birt;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParametersTest extends BaseReportTestCase {
	private static final Logger LOGGER = LoggerFactory.getLogger(ParametersTest.class);

	@Test
	public void renderPdf() {
		LOGGER.debug("renderPdf");
		Map<String, Object> extraParams = new HashMap<String, Object>();
		extraParams.put("myParam", "test");
		extraParams.put("myDateParam", new Date(new GregorianCalendar().getTimeInMillis()));
		render("parameters", IRenderer.PDF, extraParams);
	}
}
