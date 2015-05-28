package com;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXPrase
{
	byte[] data;
	
	XLSXPrase (byte[] data)
	{
		this.data = data;
	}
	
	public List<List<String>> parse () throws IOException
	{
		List<String> pc_member;
		List<List<String>> pc = new LinkedList<List<String>>();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		
        @SuppressWarnings("resource")
		Workbook diski = new XSSFWorkbook(bais);
        
        Sheet list_1 = diski.getSheetAt(0);
        for (Row row:list_1)
        {
        	pc_member = new LinkedList<String>();
            for (Cell cell:row)
            {
	        	if (cell.getCellType() == 1)
	        		pc_member.add(cell.getStringCellValue() );
	        	else
	        		pc_member.add("");
            }
            pc.add(pc_member);
        }

        return pc;
	}
}
