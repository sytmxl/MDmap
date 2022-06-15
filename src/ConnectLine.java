public class ConnectLine implements java.io.Serializable{
    /**
     * 属性:
     * int StartX, startY:起点坐标
     * int endX,endY 终点坐标
     * int width:线宽
     * boolean isLive:状态
     */
    private int startX, startY, endX, endY;
    private int width;
    boolean isLive = true;

    /**
     * 构造方法
     *
     * @param startX 起点横坐标
     * @param startY 起点纵坐标
     * @param endX   终点横坐标
     * @param endY   终点纵坐标
     */
    public ConnectLine(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.width = 1;
    }

    /***
     * 设置连接线起点终点位置
     * @param startX 起点横坐标
     * @param startY 起点纵坐标
     * @param endX   终点横坐标
     * @param endY   终点纵坐标
     */
    public void setLocation(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX(){
        return this.startX;
    }

    public void setStartX(int startX){
        this.startX=startX;
    }

    public int getStartY(){
        return this.startY;
    }

    public void setStartY(int startY){
        this.startY=startY;
    }

    public int getEndX(){
        return this.endX;
    }

    public void setEndX(int endX){
        this.endX=endX;
    }

    public int getEndY(){
        return this.endY;
    }

    public void setEndY(int endY){
        this.endY=endY;
    }

    public int getWidth(){
        return this.width;
    }

    public void setWidth(int width){
        this.width=width;
    }

}
