package fecsoft.com.databaseop.op.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class DataBaseDTO implements Serializable {

	private static final long serialVersionUID = 4466476107197384523L;

	private String dataBaseName;// 数据库名称
	private String exportType;// 导出方式:结构还是数据
	private String exportTableType;// 全表导出还是部分表导出;part or all
	private List<String> tables = new ArrayList();;

	private String userName;// 连接数据库的用户名
	private String password;// 连接数据库的密码
	private String host;// 连接数据库的域名
	private String port;// 连接数据库的端口
	private String output;// 数据导出位置,默认为数据库名称
	private String encodeType;
	
	

	public String getEncodeType() {
		return encodeType;
	}

	public void setEncodeType(String encodeType) {
		this.encodeType = encodeType;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getExportTableType() {
		return exportTableType;
	}

	public void setExportTableType(String exportTableType) {
		this.exportTableType = exportTableType;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
