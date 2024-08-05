package com.kluchinskiy.KluchinskiyProject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "translations")
public class TranslationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private String inputString;
    private String translatedString;

   public Long getId(){
       return id;
   }
   public void setId(Long id){
       this.id = id;
   }
   public String getIpAddress(){
       return ipAddress;
   }
   public void setIpAddress(String ipAddress){
       this.ipAddress = ipAddress;
   }
   public String getInputString(){
       return inputString;
   }
   public void setInputString(String inputString){
       this.inputString = inputString;
   }
   public String getTranslatedString(){
       return translatedString;
   }
   public void setTranslatedString(String translatedString){
       this.translatedString = translatedString;
   }
}