package com.github.server.requests;

public class UserAgentPojo {
	String browserName, version, majour, minor, engine;
	String platfomName;
	
	@Override
	public String toString() {
		return "userAgentPojo [browserName=" + browserName + ", version=" + version + ", majour=" + majour 
				+ ", minor="+ minor + ", engine=" + engine + ", platfomName=" + platfomName + "]";
	}

	public String toJSONString() {
		return "\"browserName\":\"" + browserName + "\", \"version\":\"" + version + "\", \"majour\":\"" + majour + 
			"\", \"minor\":\""+ minor + "\", \"engine\":\"" + engine + "\", \"platfomName\":\"" + platfomName + "\"";
	}
	
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMajour() {
		return majour;
	}
	public void setMajour(String majour) {
		this.majour = majour;
	}
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getPlatfomName() {
		return platfomName;
	}
	public void setPlatfomName(String platfomName) {
		this.platfomName = platfomName;
	}
}