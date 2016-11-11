package fecsoft.com.databaseop.op.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import fecsoft.com.databaseop.op.core.domain.DataBaseDTO;

public class ExportUtils {

	// 读取配置文件,返回实体类参数
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static List<DataBaseDTO> getPropertyByConfig() throws Exception {

		List<DataBaseDTO> listDto = new ArrayList<DataBaseDTO>();

		SAXReader reader = new SAXReader();
		URL url = ClassLoader.getSystemClassLoader().getResource("common-config.xml");
		Document document = reader.read(url);

		String userName = "";
		String password = "";
		String host = "";
		String port = "";

		Element gloableElement = (Element) document.getRootElement().selectSingleNode("/databases/gloable-host");
		if (gloableElement != null) {
			List listGlo = gloableElement.elements();
			for (Iterator k = listGlo.iterator(); k.hasNext();) {
				Element resourceitem = (Element) k.next();
				String nodeName = resourceitem.getName();
				if ("user-name".equals(nodeName)) {
					userName = resourceitem.getText();
				} else if ("password".equals(nodeName)) {
					password = resourceitem.getText();
				} else if ("host".equals(nodeName)) {
					host = resourceitem.getText();
				} else if ("port".equals(nodeName)) {
					port = resourceitem.getText();
				}
			}
		}

		List<Element> databaseList = document.getRootElement().selectNodes("/databases/database");// 节点列表
		for (Element database : databaseList) {
			DataBaseDTO dto = new DataBaseDTO();
			List<Element> elements = database.elements();
			for (Element resourceItem : elements) {// 遍历每个database节点
				String nodeName = resourceItem.getName();
				if ("database-name".equals(nodeName)) {
					String type = resourceItem.attributeValue("type");
					String dataBaseName = resourceItem.getText();
					dto.setExportType(type);
					dto.setDataBaseName(dataBaseName);
					System.out.println();

				} else if ("tables".equals(nodeName)) {
					String countType = resourceItem.attributeValue("count");
					dto.setExportTableType(countType);
					if ("part".equals(countType)) {
						// 获取部分表名
						List<Element> tableElements = resourceItem.elements();
						List<String> tableList = new ArrayList();
						for (Element tableItem : tableElements) {
							String tableName = tableItem.getText();
							tableList.add(tableName);
							System.out.println();
						}
						dto.setTables(tableList);
					}

				} else if ("data-out".equals(nodeName)) {
					String dataOut = resourceItem.getText();
					dto.setOutput(dataOut);
				} else if ("database-host".equals(nodeName)) {
					List<Element> listHost = resourceItem.elements();
					for (Element e : listHost) {
						String name = e.getName();
						String text = e.getText();
						if ("user-name".equals(name)) {
							dto.setUserName(text);
						} else if ("password".equals(name)) {
							dto.setPassword(text);
						} else if ("host".equals(name)) {
							dto.setHost(text);
						} else if ("port".equals(name)) {
							dto.setPort(text);
						}
					}
				} else if("database-encode".equals(nodeName)) {
					String encodeType = resourceItem.getText();
					dto.setEncodeType(encodeType);
				}

				String userNameAfter = dto.getUserName();
				if (userNameAfter == null || userNameAfter.trim().equals("")) {
					if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)
							&& StringUtils.isNotBlank(host) && StringUtils.isNotBlank(port)) {
						dto.setUserName(userName);
						dto.setPassword(password);
						dto.setHost(host);
						dto.setPort(port);
					}
				}

			}
			
			String outPutPath = dto.getOutput();
			if(StringUtils.isBlank(outPutPath)) {
				dto.setOutput("E:\\"+dto.getDataBaseName()+".sql");
			}
			listDto.add(dto);
		}
		
		return listDto;
	}

	// 根据实体类参数,执行sql导出命令
	@SuppressWarnings("unused")
	public static void excuteSqlCommand(List<DataBaseDTO> dataBaseList) throws Exception {

		InputStream resourceAsStream = ExportUtils.class.getResourceAsStream("/command.properties");
		Properties prop = new Properties();
		prop.load(resourceAsStream);
		

		if (dataBaseList != null && dataBaseList.size() > 0) {
			for (DataBaseDTO dto : dataBaseList) {

				StringBuffer sb = new StringBuffer();
				List<String> tables = dto.getTables();
				if (tables != null && tables.size() > 0) {
					for (String table : tables) {
						sb.append(table + " ");
					}
				}
				
				String command = prop.getProperty("output.strcture");
				String commandResult = "";
				
				if(StringUtils.isNoneBlank(command)) {
					
					commandResult = MessageFormat.format(command, dto.getUserName(),
																	dto.getEncodeType(),
																	dto.getPassword(),
																	dto.getHost(),
																	dto.getPort(),
																	dto.getDataBaseName(),
																	sb.toString(),dto.getOutput());
					
				}
				Process exec = Runtime.getRuntime().exec(commandResult);

			}
		} else {
			throw new RuntimeException();
		}

	}

}
