package fr.smile.birt;

import java.util.logging.LogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class JulOverSlfConfigurer {
	private static final Logger LOGGER = LoggerFactory.getLogger(JulOverSlfConfigurer.class);

	public void init() {
		LOGGER.debug("init");
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();
	}
}
