/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Functions | Templates
 * and open the template in the editor.
 */
package Utility.Tools;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author admin
 */
public class Functions
{
    public static Date addDay(Date date, long day)
    {
        LocalDateTime addeDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).plusDays(day);
        
        Date convertedAddDate = Date.from(addeDate.atZone(ZoneId.systemDefault()).toInstant());
        
        return convertedAddDate;        
    }
}
