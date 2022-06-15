public class ConnectLine implements java.io.Serializable{
    /**
     * ����:
     * int StartX, startY:�������
     * int endX,endY �յ�����
     * int width:�߿�
     * boolean isLive:״̬
     */
    private int startX, startY, endX, endY;
    private int width;
    boolean isLive = true;

    /**
     * ���췽��
     *
     * @param startX ��������
     * @param startY ���������
     * @param endX   �յ������
     * @param endY   �յ�������
     */
    public ConnectLine(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.width = 1;
    }

    /***
     * ��������������յ�λ��
     * @param startX ��������
     * @param startY ���������
     * @param endX   �յ������
     * @param endY   �յ�������
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
