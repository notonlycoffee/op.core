package fecsoft.com.databaseop.op.core.main;

import java.util.List;

import fecsoft.com.databaseop.op.core.domain.DataBaseDTO;
import fecsoft.com.databaseop.op.core.utils.ExportUtils;

public class ExcuteMain {
	
	
	public static void main(String[] args) {
		try {
			
			List<DataBaseDTO> propertyByConfig = ExportUtils.getPropertyByConfig();
			
			ExportUtils.excuteSqlCommand(propertyByConfig);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
