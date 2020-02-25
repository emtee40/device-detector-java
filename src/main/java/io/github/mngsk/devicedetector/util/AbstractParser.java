package io.github.mngsk.devicedetector.util;

import java.util.Optional;
import java.util.regex.Matcher;

public abstract class AbstractParser<T> {

	public abstract Optional<T> parse(String userAgent);

	protected String buildByMatch(String item, Matcher matcher) {
		if (item == null || item.trim().isEmpty()) {
			return null;
		}

		if (!item.contains("$")) {
			return item.trim();
		}

		for (int i = 1; i <= 3; i++) {
			if (item.indexOf("$" + i) == -1) {
				continue;
			}

			String group = matcher.group(i);
			String replacement = Optional.ofNullable(group).orElse("");
			item = item.replace("$" + i, replacement);
		}
		return item.trim();
	}

	protected String buildVersion(String version, Matcher matcher) {
		version = buildByMatch(version, matcher);
		if (version == null) {
			return null;
		}

		version = version.replace("_", ".");
		// TODO truncate maxMinorParts
		return version;
	}

}