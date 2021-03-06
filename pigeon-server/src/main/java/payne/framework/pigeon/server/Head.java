package payne.framework.pigeon.server;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import payne.framework.pigeon.core.annotation.Accept.Mode;
import payne.framework.pigeon.server.exception.UnrecognizedHeadException;
import payne.framework.pigeon.server.exception.UnrecognizedModeException;

public class Head {
	private final Mode mode;
	private final String file;
	private final String parameter;
	private final String protocol;

	public Head(String head) throws IOException {
		String[] segments = head.split("\\s+");
		if (segments.length != 3) {
			throw new UnrecognizedHeadException(head);
		}
		this.mode = Mode.likeOf(segments[0].trim().toUpperCase());
		if (this.mode == null) {
			throw new UnrecognizedModeException(segments[0].trim().toUpperCase());
		}

		int index = segments[1].indexOf('?');
		if (index == -1) {
			this.file = segments[1].trim();
			this.parameter = "";
		} else {
			this.file = segments[1].substring(0, index).trim();
			String query = segments[1].substring(index + 1).trim();
			this.parameter = URLDecoder.decode(query, Charset.defaultCharset().name());
		}
		this.protocol = segments[2].trim().toUpperCase();
	}

	public Mode getMode() {
		return mode;
	}

	public String getFile() {
		return file;
	}

	public String getParameter() {
		return parameter;
	}

	public String getProtocol() {
		return protocol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
		result = prime * result + ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Head other = (Head) obj;
		if (mode != other.mode)
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (protocol == null) {
			if (other.protocol != null)
				return false;
		} else if (!protocol.equals(other.protocol))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return mode + " " + file + (parameter == null || parameter.trim().equals("") ? "" : "?" + parameter) + " " + protocol;
	}

}
