package com.calendly.calendly.View;
public class MyFont {

    private static final MyFont instance = new MyFont();

    public static MyFont getInstance() {
        return instance;
    }

    private MyFont(){}

    private enum tipiFont{Quicksand, Cantarell}

    public tipiFont getQuicksand(){
        return tipiFont.Quicksand;
    }

    public tipiFont getCantarell(){
        return tipiFont.Cantarell;
    }

    private String font = "Quicksand Medium";

    private int sizeTxt = 14;
    private int sizeLabel = 28;

    public void setFont(tipiFont t){
        if (t.toString().equals("Quicksand")){
            font = t.toString() + " Medium";
        }else{
            font = t.toString();
        }
    }

    public void setSizeTxt(int s){
        sizeTxt = s;
    }
    public void setSizeLabel(int s){
        sizeLabel = s+14;
    }

    public String getFont(){
        return font;
    }

    public int getSizeTxt(){
        return sizeTxt;
    }
    public int getSizeLabel(){
        return sizeLabel;
    }
}
