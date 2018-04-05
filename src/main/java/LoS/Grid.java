/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoS;

import com.superawesomechicken.searchalgorithm.Plotter2D3;
import com.superawesomechicken.searchalgorithm.SortedList;
import com.superawesomechicken.searchalgorithm.SortedQueue;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Saud
 */
public class Grid {
    
    public Node[][] grid; //TODO make private
    private Plotter2D3 plotter;
    private SortedList<Node> corners = new SortedList<>();
    
    private final Color BLANK_COLOUR = Color.WHITE;
    private final Color END_COLOUR = Color.RED;
    private final Color START_COLOUR = Color.GREEN;
    private final Color SCAN_COLOUR = Color.LIGHT_GRAY;
    private final Color PATH_COLOUR = Color.YELLOW;
    private final Color BLOCK_COLOUR = Color.DARK_GRAY;
    
    public Grid(int width, int height, Plotter2D3 plotter) {
        grid = new Node[width][height];
        this.plotter = plotter;
        
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                //double dist = Math.sqrt(Math.pow(targetI-i, 2) + Math.pow(targetJ-j, 2));
                grid[i][j] = new Node(i, j);
            }
        }
    }
    public Grid(int width, int height) {
        this(width, height, null);
    }
    public Grid(Plotter2D3 p) {
        this(p.getGridWidth(), p.getGridHeight(), p);
    }
    
    public boolean getBlocked(int posI, int posJ) {
        return grid[posI][posJ].isBlocked();
    }
    
    public Node[] searchPath(int sI, int sJ, int eI, int eJ) {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                double dist = Math.sqrt(Math.pow(eI-i, 2) + Math.pow(eJ-j, 2));
                grid[i][j].setDistToGoal(dist);
            }
        }
        
        SortedQueue<Node> q = new SortedQueue<>();
        grid[sI][sJ].setStepsFromSource(0);
        grid[sI][sJ].setScanned(true);
        grid[sI][sJ].setDistToGoal(Math.sqrt(Math.pow(sI-eI,2) + Math.pow(sJ-eJ, 2)));
        q.push(grid[sI][sJ], grid[sI][sJ].getDistToGoal());
        corners.add(grid[sI][sJ], 0);
        while(!q.isEmpty()) {
            Node selN = q.pop();
            if(selN.getx() == eI && selN.gety() == eJ) break;
            ArrayList<Node> wall = getInLineToWall(selN);
            if(wall.isEmpty()) {
                for(Node n : getUnblocked(selN)) {
                    if(!n.isScanned()) {
                        n.setStepsFromSource(selN.getStepsFromSource()+1);
                        q.push(n, n.getDistToGoal());
                        n.setScanned(true);
                        if(plotter!=null) {
                            plotter.setText(n.getx(), n.gety(), n.getStepsFromSource()+"");
                            plotter.repaint(); //try{Thread.sleep(100);}catch(Exception e){}
                        }
                    }
                }
            } else if(wall.size()>0) {
                for(Node n : wall) {
                    if(n.isScanned()) continue;
                    n.setStepsFromSource(selN.getStepsFromSource()+1);
                    if(plotter!=null) {
                        plotter.setText(n.getx(), n.gety(), n.getStepsFromSource()+"");
                        plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
                    }
                    if(isOnCorner(n)) {
                       corners.add(n, n.getStepsFromSource());
                       for(Node corner : corners.getArray()) {
                           if(!corner.equals(n)) {
                               ArrayList<Node> losList = getLoS(corner,n);
                               if(losList!= null && losList.size()+corner.getStepsFromSource() < n.getStepsFromSource()) {
                                   for(int i=1; i<losList.size(); i++) {
                                       losList.get(i).setStepsFromSource(losList.get(i-1).getStepsFromSource()+1);
                                       losList.get(i).setScanned(true);
                                       plotter.setText(losList.get(i).getx(), losList.get(i).gety(), losList.get(i).getStepsFromSource()+"");
                                       plotter.setColour(losList.get(i).getx(), losList.get(i).gety(), SCAN_COLOUR);
                                   }
                                   n.setStepsFromSource(losList.size()+corner.getStepsFromSource()+1);
                                   if(plotter!=null) {
                                        plotter.setText(n.getx(), n.gety(), n.getStepsFromSource()+"");
                                        plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
                                    }
                               }
                           }
                       }
                       
                    }
                    q.push(n, n.getDistToGoal());
                    n.setScanned(true);
                }
            }
            if(plotter!=null) {
                plotter.setColour(selN.getx(), selN.gety(), SCAN_COLOUR);
                plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
            }
        }
        Node n = grid[eI][eJ];
        n.setStepsFromSource(Integer.MAX_VALUE);
        ArrayList<Node> arr = new ArrayList<>();
        while(true) {
            arr.add(n);
            n = getNodeClosestToSource(n);
            if(n.getStepsFromSource() == 0) break;
            
            if(plotter!=null) {
                int[] posArr = getPos(n);
                plotter.setColour(posArr[0], posArr[1], PATH_COLOUR);
                plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
            }
        }
        Node[] out = new Node[arr.size()];
        for(int i=0; i<out.length; i++) {
            out[i] = arr.get(arr.size()-i-1);
        }
        
        if(plotter!=null) {
            plotter.setColour(eI, eJ, END_COLOUR);
            plotter.setColour(sI, sJ, START_COLOUR);
            plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
        }
        return out;
    }
    
    //TODO make private
    public boolean isOnCorner(Node n) {
        Node temp;
        //ne
        if((temp=getNodeInDir(n, 1, 1)) != null && temp.isBlocked() &&
           (temp=getNodeInDir(n, 1, 0)) != null && !temp.isBlocked() &&
           (temp=getNodeInDir(n, 0, 1)) != null && !temp.isBlocked())
                return true;
        //se
        if((temp=getNodeInDir(n, 1, -1)) != null && temp.isBlocked() &&
           (temp=getNodeInDir(n, 1,  0)) != null && !temp.isBlocked() &&
           (temp=getNodeInDir(n, 0, -1)) != null && !temp.isBlocked())
                return true;
        //sw
        if((temp=getNodeInDir(n, -1, -1)) != null && temp.isBlocked() &&
           (temp=getNodeInDir(n, -1, 0)) != null && !temp.isBlocked() &&
           (temp=getNodeInDir(n, 0, -1)) != null && !temp.isBlocked())
                return true;
        //nw
        if((temp=getNodeInDir(n, -1, 1)) != null && temp.isBlocked() &&
           (temp=getNodeInDir(n, -1, 0)) != null && !temp.isBlocked() &&
           (temp=getNodeInDir(n,  0, 1)) != null && !temp.isBlocked())
                return true;
        
        return false;
    }
    
    private Node getNodeInDir(Node n, int dx, int dy) {
        int x1 = n.getx()+dx;
        int y1 = n.gety()+dy;
        if(x1<0 || x1>=grid.length || y1<0 || y1>=grid[0].length) return null;
        return grid[x1][y1];
    }
    
    private ArrayList<Node> getLoS(Node n1, Node n2) {
        ArrayList<Node> out = new ArrayList<>();
        int x0 = n1.getx();
        int y0 = n1.gety();
        
        int dx = Math.abs(n2.getx() - x0);
        int dy = Math.abs(n2.gety() - y0);

        int ix = (n1.getx() < n2.getx()) ? 1 : -1;
        int iy = (n1.gety() < n2.gety()) ? 1 : -1;
        int e = 0;

        for(int i=0; i<dx+dy; i++) {
            if(grid[x0][y0].isBlocked()) return null;
            out.add(grid[x0][y0]);
            if(plotter!=null) {
                plotter.setColour(x0, y0, Color.PINK);
                plotter.repaint(); try{Thread.sleep(100);}catch(Exception ex){}
            }
            
            int e1 = e + dy;
            int e2 = e - dx;
            if(Math.abs(e1) < Math.abs(e2)) {
                x0 += ix;
                e = e1;
            } else {
                y0 += iy;
                e = e2;
            }
        }
        return out;
    }
    
    private ArrayList<Node> getAllNodes(Node n) {
        ArrayList<Node> out = new ArrayList<>();
        out.add((n.gety()<grid[0].length-1)?grid[n.getx()][n.gety()+1]:null);
        out.add((n.getx()<grid.length-1)?grid[n.getx()+1][n.gety()]:null);
        out.add((n.gety()>0)?grid[n.getx()][n.gety()-1]:null);
        out.add((n.getx()>0)?grid[n.getx()-1][n.gety()]:null);
        return out;
    }
    
    private ArrayList<Node> getNodeSet(Node n) {
        ArrayList<Node> arr = getAllNodes(n);
        while(arr.contains(null)) {
            arr.remove(null);
        }
        return arr;
    }
    
    private Node getNodeClosestToSource(Node n) {
        Node out = null;
        for(Node node : getUnblocked(n)) {
            if(out == null || node.getStepsFromSource() < out.getStepsFromSource()) {
                if(node.getStepsFromSource()!=-1) out = node;
            }
        }
        return out;
    }
    
    private ArrayList<Node> getUnblocked(Node n) {
        ArrayList<Node> out = new ArrayList<>();
        for(Node node : getNodeSet(n)) {
            if(!node.isBlocked()) out.add(node);
        }
        return out;
    }
    
    private ArrayList<Node> getInLineToWall(Node n) {
        ArrayList<Node> arr = getAllNodes(n);
        ArrayList<Node> out = new ArrayList<>();
        for(int i=0; i<arr.size(); i++) {
            if(arr.get(i)!=null && arr.get(i).isBlocked()) {
                Node temp;
                if((temp=arr.get((i+1)%arr.size()))!=null && !temp.isBlocked())
                    out.add(arr.get((i+1)%arr.size()));
                if((temp=arr.get((i+arr.size()-1)%arr.size()))!=null && !temp.isBlocked())
                    out.add(arr.get((i+arr.size()-1)%arr.size()));
            }
        }
        return out;
    }
    
    public int[] getPos(Node n) {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                if(n.equals(grid[i][j])) return new int[] {i, j};
            }
        }
        return null;
    }
    
    public void setBlocked(int posI, int posJ, boolean isBlocked) {
        grid[posI][posJ].setBlocked(isBlocked);
        if(plotter!=null) {
            plotter.setColour(posI, posJ, (isBlocked) ? BLOCK_COLOUR : BLANK_COLOUR);
            plotter.setText(posI, posJ, null);
            plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
        }
    }
    
    public void setBlocked(int posI1, int posJ1,  int posI2,int posJ2, boolean isBlocked) {
        for(int i=posI1; i<=posI2; i++) {
            for(int j=posJ1; j<=posJ2; j++) {
                setBlocked(i, j, isBlocked);
            }
        }
    }
    
    public void reset() {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                grid[i][j].setScanned(false);
                grid[i][j].setStepsFromSource(-1);
                grid[i][j].setDistToGoal(-1);
                plotter.clear(BLANK_COLOUR, BLOCK_COLOUR);
            }
        }
        plotter.repaint();
    }
    
    public void clearAll() {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                grid[i][j].setScanned(false);
                grid[i][j].setBlocked(false);
                grid[i][j].setStepsFromSource(-1);
                grid[i][j].setDistToGoal(-1);
            }
        }
        plotter.clear(BLANK_COLOUR);
        plotter.repaint();
    }
    
}
