
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.sql.rowset.CachedRowSet;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.SessionScoped;



/**
 *
 * @author egezzz*/

// DB -> erp


@javax.faces.bean.ManagedBean(name="bean")
@SessionScoped
public class Main extends MD5 {
    static int a=2;
    String id,firmaadi,yonetici,eposta,sifre,sifrekontrol,firmakontrol;
    String urunadi,stok;
    int stokint;
    String silinecek_urun;
    private Connection conn = null;
    String sifreuyari;
    String tarih;
    String basarili;
    
    String adminadi="root",adminsifre="root";
    
    String dbUrl = "jdbc:derby://localhost:1527/erp";
    String dbID = "BELIRLEDIGIN JAVADB ID";
    String dbPsw = "BELIRLEDIGIB JAVADB PSW";
    String uyari1,uyari2=" ";

    CachedRowSet rowSet=null;

    public String getUyari2() {
        return uyari2;
        
    }

    public void setUyari2(String uyari2) {
        this.uyari2 = uyari2;
    }

    
    
    public String getAdminadi() {
        return adminadi;
    }

    public void setAdminadi(String adminadi) {
        this.adminadi = adminadi;
    }

    public String getAdminsifre() {
        return adminsifre;
    }

    public void setAdminsifre(String adminsifre) {
        this.adminsifre = adminsifre;
    }

    
    
    
    public String getBasarili() {
        return basarili;
    }

    public void setBasarili(String basarili) {
        this.basarili = basarili;
    }

    
    
    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    
    
    public String getSifreuyari() {
        return sifreuyari;
    }

    public void setSifreuyari(String sifreuyari) {
        this.sifreuyari = sifreuyari;
    }
    
    public String getUyari1() {
        return uyari1;
    }

    public void setUyari1(String uyari1) {
        this.uyari1 = uyari1;
    }
    
    
    
    public String getSilinecek_urun() {
        return silinecek_urun;
    }

    public void setSilinecek_urun(String silinecek_urun) {
        this.silinecek_urun = silinecek_urun;
    }

    public String getUrunadi() {
        return urunadi;
    }

    public void setUrunadi(String urunadi) {
        this.urunadi = urunadi;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getFirmakontrol() {
        return firmakontrol;
    }

    public void setFirmakontrol(String firmakontrol) {
        this.firmakontrol = firmakontrol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirmaadi() {
        return firmaadi;
    }

    public void setFirmaadi(String firmaadi) {
        this.firmaadi = firmaadi;
    }

    public String getYonetici() {
        return yonetici;
    }

    public void setYonetici(String yonetici) {
        this.yonetici = yonetici;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getSifrekontrol() {
        return sifrekontrol;
    }

    public void setSifrekontrol(String sifrekontrol) {
        this.sifrekontrol = sifrekontrol;
    }
     
    
    
    public String kayitFonk() throws SQLException{
        
    
            
        if(sifrekontrol.equals(sifre)){
            
            SimpleDateFormat bicim3=new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            GregorianCalendar gcalender=new GregorianCalendar();
            tarih = bicim3.format(gcalender.getTime());
            sifre=hash(sifre);
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            //? ekle
            PreparedStatement object2= conn.prepareStatement("INSERT INTO KULLANICILAR VALUES(?,?,?,?,?)");
            
            object2.setString(1, getFirmaadi());
            object2.setString(2, getSifre());
            object2.setString(3, getYonetici());
            object2.setString(4, getEposta());  
            object2.setString(5, getTarih());
            object2.executeUpdate();
            
            //--------------
            
            PreparedStatement object3=conn.prepareStatement("CREATE TABLE "+firmaadi+" (urunadi varchar(50), stok int)");
            object3.executeUpdate();
            
            return "giris.xhtml";
        }
        finally{
            conn.close();
        }
            
        }
        else{ //girilen şifreler eşit değilse
          
            sifreuyari="Girilen şifreler uyuşmuyor, lütfen tekrar deneyiniz.";
            
            return "kayit.xhtml";
        }
   
               
    }
    
    public String girisFonk() throws SQLException{
        
        //kullanicilar Tabledan firmaadi-sifre uygun var mı? kontrolü 
        sifre=hash(sifrekontrol);
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
           // int iVal = 0;
            
           Statement stmt= conn.createStatement();
           ResultSet rs=stmt.executeQuery("SELECT SIFRE FROM EGEHAN.KULLANICILAR WHERE FIRMAADI='"+firmakontrol+"'");
           
           
            
            if(rs.next()){
            if(sifre.equals(rs.getString("SIFRE"))){
                return "islem.xhtml";
            }
            }
           
        }
        finally{
            conn.close();
        }
        
    return "giris.xhtml";
    
}
    
    public ResultSet kaynakgoruntule() throws SQLException{ //hocanın kodundaki bul fonksiyonu
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps = conn.prepareStatement("SELECT URUNADI,STOK FROM "+firmaadi);
            
            
            
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( ps.executeQuery() );
            return rowSet;
            
            
        }
        finally{
            conn.close();
        }
        
    }
    
    
    public String sil() throws SQLException{
      //  stokint=Integer.parseInt(stok);
      
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps= conn.prepareStatement("DELETE FROM "+firmaadi+" WHERE URUNADI = '"+silinecek_urun+"'");         
             ps.executeUpdate();
        }
        finally{
            conn.close();
            
        }
        return "kaynakgoruntule";
        
    }
    public String stoktanazalt()throws SQLException{
        
        
        
         try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        if(conn==null){
            System.out.println("DB çöktü");
        }
        try{
            //stok sayısı çek
            //stoku güncelle
            //tabloyu güncelle
            
            //1-
          
           
           Statement stmt= conn.createStatement();
           ResultSet rs=stmt.executeQuery("SELECT STOK FROM "+firmaadi+" WHERE URUNADI='"+silinecek_urun+"'");
           
           int a;
           
           if(rs.next()){
               a=rs.getInt("STOK");
               
               if(rs.getInt("STOK")!=0){
                   uyari1=null;
                   a--;
                   stokint=a;
                    PreparedStatement ps= conn.prepareStatement("UPDATE "+firmaadi+" SET STOK= "+stokint+" WHERE URUNADI='"+silinecek_urun+"'");
                   ps.executeUpdate();
               }
               else{//0 sa elleme, bitir
                
                   uyari1="Stokta ürün bulunmamaktadır.";
               }
               
               
           }
            
            
        }
        finally{
            conn.close();
        }
        
        return "kaynakgoruntule";
    }
    
    public String stokarttir() throws SQLException{
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        if(conn==null){
            System.out.println("DB çöktü");
        }
        try{
            
           Statement stmt= conn.createStatement();
           ResultSet rs=stmt.executeQuery("SELECT STOK FROM "+firmaadi+" WHERE URUNADI='"+silinecek_urun+"'");
           
           int a;
           
           if(rs.next()){
                   a=rs.getInt("STOK");               
                   uyari1=null;
                   a++;
                   stokint=a;
                    PreparedStatement ps= conn.prepareStatement("UPDATE "+firmaadi+" SET STOK= "+stokint+" WHERE URUNADI='"+silinecek_urun+"'");
                   ps.executeUpdate();
            
           }    
        }
        finally{
            conn.close();
        }
        
        return "kaynakgoruntule";
        
    }
    
    
    
    public String ekle() throws SQLException{
        
        stokint = Integer.parseInt(stok);
        
         try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        if(conn==null){
            System.out.println("DB çöktü");
        }
        try{//sql tarafı
            
         PreparedStatement ps=conn.prepareStatement("INSERT INTO "+firmaadi+" VALUES('"+urunadi+"',"+stokint+")");
          
         ps.executeUpdate();
         
         basarili="Stoklarınıza "+stokint+" adet "+urunadi+" eklenmiştir.";
         
        }
        finally{
            conn.close();
        }
        return "kaynakekle.xhtml";
    }
   
    public String  admin_girisFonk() throws SQLException{
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            if(adminadi.equals("ege") && adminsifre.equals("111")){
                
                return "adminislem";
            }
            else{
                uyari2="Admin şifreniz yanlış";
            }
            return "adminpanel";
        }finally{
            conn.close();
        }
    }
    
    public ResultSet admin_uyeGoruntule() throws SQLException{
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps = conn.prepareStatement("SELECT FIRMAADI,YONETICIADI,EPOSTA,TARIH FROM KULLANICILAR");
            
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( ps.executeQuery() );
            return rowSet;
            
            
        }
        finally{
            conn.close();
        }
        
    }
    
    public ResultSet admin_uyeSayisi() throws SQLException{
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps = conn.prepareStatement("SELECT count(FIRMAADI) as musterisayi FROM KULLANICILAR");
            
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( ps.executeQuery() );
            return rowSet;
            
            
        }
        finally{
            conn.close();
        }
        
    }
    
    public ResultSet admin_eskiKullanici() throws SQLException{
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps = conn.prepareStatement("SELECT FIRMAADI,TARIH FROM KULLANICILAR ORDER BY TARIH ASC OFFSET 0 ROWS " +
"FETCH NEXT 3 ROWS ONLY");
            
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( ps.executeQuery() );
            return rowSet;
            
            
        }
        finally{
            conn.close();
        }
    }
    
    public ResultSet senaryodisi_SQL1() throws SQLException{
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps = conn.prepareStatement("Select XA_id, sum(urunsayisi) as urunsayisi"
                    + " from XB Group By XA_id order by urunsayisi DESC");
            
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( ps.executeQuery() );
            return rowSet;
            
            
        }
        finally{
            conn.close();
        }
        
    }
    
    public ResultSet senaryodisi_SQL2() throws SQLException{
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps = conn.prepareStatement("Select XA.ad,urunsayisi FROM XA"
               + " INNER JOIN (Select XA_id, sum(urunsayisi) as urunsayisi from XB Group By XA_id) as aratablo"
                    + " ON XA.id=aratablo.XA_id ORDER BY urunsayisi DESC");
            
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( ps.executeQuery() );
            return rowSet;
            
            
        }
        finally{
            conn.close();
        }
        
    }
    
    
    public ResultSet senaryodisi_SQL3() throws SQLException{
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps = conn.prepareStatement("Select XA.id as id, XA.ad as ad, XB.urunsayisi as urunsayisi"
                    + " From XA INNER JOIN XB ON XA.id=XB.XA_id ORDER BY XA.id ASC");
            
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( ps.executeQuery() );
            return rowSet;
            
            
        }
        finally{
            conn.close();
        }
        
    }
    
    public ResultSet senaryodisi_SQL5() throws SQLException{
        
        try{
            conn = DriverManager.getConnection(dbUrl,dbID,dbPsw);
        }catch(Exception e){
            System.out.println("Baglantıda sorun var!");
        }
        
        if(conn==null){
            System.out.println("DB çöktü");
        }
        
        try{
            
            PreparedStatement ps = conn.prepareStatement("Select XA.ad,urunsayisi FROM XA"
               + " INNER JOIN (Select XA_id, max(urunsayisi) as urunsayisi from XB Group By XA_id) as aratablo2"
                    + " ON XA.id=aratablo2.XA_id");
            
            rowSet = new com.sun.rowset.CachedRowSetImpl();
            rowSet.populate( ps.executeQuery() );
            return rowSet;
            
            
        }
        finally{
            conn.close();
        }
        
    }
}
