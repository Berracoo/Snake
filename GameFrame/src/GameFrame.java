import javax.swing.JFrame;
public class GameFrame extends JFrame{
  //constructor -> a special method that is used to initialize objects.
    //Constructor
    GameFrame(){
      // this is a reference to the current object - the object whose method
      // or constructor is being called. You can refer to any member of the current object within an instance method
      // or a constructor by using this.

      // Long version GamePanel panel = new GamePanel();
      // this.add(panel);
      this.add(new GamePanel());
      // adding a title
      this.setTitle("Snake");
      // is used to specify one several option for the close button.
      // exit on close means exit the application
      // hide on close = hide the frame, but keep the application running
      // dispose on close = dispose the frame object, // // //
      // do nothing on close = ignore the click
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // is used to prevent the use to re-size the graphical interface the way you intended to
      // if the parameter is false the user cannot re-zie the frame
      this.setResizable(false);
      //is used for sizing the frame so that all its contents are at or above their
      //preferred sizes ( setSize or setBounds can be used too)
      // i.e. if we add component to JFrame pack will make JFrame to fit the components that we add.
      this.pack();
      // this method is used if we this window to appear in the middle of our computer screen
      this.setVisible(true);
      // sets the location of the window relative to the specified component
      // if the component is not currently showing, or is null, the window is centered on the screen.
      this.setLocationRelativeTo(null);




    }


}