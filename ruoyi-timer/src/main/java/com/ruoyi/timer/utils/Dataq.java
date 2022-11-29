package com.ruoyi.timer.utils;

import com.spreada.utils.chinese.ZHConverter;

import java.nio.charset.Charset;
import java.sql.*;
/*
	JDBC完成Delete
*/

public class Dataq {

    private static final Charset BIG5 = Charset.forName("BIG5");
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static void main(String[] args) throws Exception{
        String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL="jdbc:sqlserver://192.168.70.249:1433;DatabaseName=IEMDG";
        String userName="faceid";            //sqlserver用户名
        String userPwd="faceid";    //sqlserver用户密码
        try{
            Class.forName(driverName);   //加载sqlserver的驱动类
            System.out.println("加载SQLServer驱动类成功!");
        }
        catch(ClassNotFoundException a){
            System.out.println("加载SQLServer驱动失败!");
            a.printStackTrace();
        }
        Connection dbcon=null;           //处理与特定数据库的连接
        try{
            dbcon=DriverManager.getConnection(dbURL,userName,userPwd);
            System.out.println("数据库连接成功!");
            Statement stat = dbcon.createStatement();//创建一个 Statement 对象来将 SQL 语句发送到数据库。
            ResultSet resultSet=stat.executeQuery("select * from InOutLog where aid='772992'");
            while (resultSet.next()) {

//                    String decoded = new String(utf8Encoded, UTF8);
//
//                    byte[] big5Encoded = decoded.getBytes(BIG5);
//                    byte[] big5Encoded= resultSet.getString("name").getBytes(Charset.forName("BIG5"));
//                    System.out.println(new String(big5Encoded,"BIG5"));
                //System.out.println( ConvertStr.convertToSimplifiedChinese(resultSet.getString("tkvnd")));

  ZHConverter converter = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
               System.out.println( converter.convert(resultSet.getString("tkvnd")));
            }

            dbcon.close();
        }
        catch(SQLException e){
            System.out.println("数据库连接失败!");
            e.printStackTrace();
        }
    }


}