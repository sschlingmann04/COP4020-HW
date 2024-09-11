package plc.examples;
class ColorRGB {
  private int red;
  private int green;
  private int blue;

  public ColorRGB() {
    red = 0;
    green = 0;
    blue = 0;
  }
  public ColorRGB(ColorRGB colorRGB) {
    red = colorRGB.getRed();
    green = colorRGB.getGreen();
    blue = colorRGB.getBlue();
  }
  public ColorRGB(int red, int green, int blue) {
    this.red = Math.abs(red) % 256;
    this.green = Math.abs(green) % 256;
    this.blue = Math.abs(blue) % 256;
  }

  public int getRed() {
    return red;
  }
  public int getGreen() {
    return green;
  }
  public int getBlue() {
    return blue;
  }
  public void setRed(int red) {
    this.red = Math.abs(red) % 256;
  }
  public void setGreen(int green) {
    this.green = Math.abs(green) % 256;
  }
  public void setBlue(int blue) {
    this.blue = Math.abs(blue) % 256;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ColorRGB 
           && red == ((ColorRGB) obj).getRed()
           && green == ((ColorRGB) obj).getGreen()
           && blue == ((ColorRGB) obj).getBlue();
  }
  @Override
  public String toString() {
    return "ColorRGB:\n" +
           "  Red   = " + red + "\n" +
           "  Green = " + green + "\n" +
           "  Blue  = " + blue + "\n";
  }
}

abstract class Shape {
  protected String colorLabel;
  protected ColorRGB colorRGB;

  protected Shape() {
    colorLabel = "";
    colorRGB = new ColorRGB();
  }

  protected Shape(String colorLabel, ColorRGB colorRGB) {

    this.colorLabel = colorLabel;
    this.colorRGB = colorRGB;

    /******************************************************************
     * Exercise:  Modify Shape so that it does not create a deep copy *
     *              of colorRGB, creating a shallow copy instead.     *
     *                                                                *
     *   Change Line #70                                              *
     *                                                                *
     *     <from>  this.colorRGB = new ColorRGB(colorRGB);            *
     *                                                                *
     *       <to>  this.colorRGB = colorRGB;                          *
     *                                                                *
     ******************************************************************/
  }

  public String getColorLabel() {
    return colorLabel;
  }
  public void setColorLabel(String colorLabel) {
    this.colorLabel = colorLabel;
  }
  public ColorRGB getColorRGB() {
    return colorRGB;
  }
  public void setColorRGB(ColorRGB colorRGB) {
    this.colorRGB = colorRGB;
  }

  public abstract double area();

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Shape
           // cannot use:  this.colorLabel == ((Shape) obj).getColorLabel()
           && colorLabel.equals( ((Shape) obj).getColorLabel() )
           && colorRGB.equals( ((Shape) obj).getColorRGB() );
  }
  @Override
  public String toString() {
    return "ColorLabel:  " + colorLabel + "\n" +
           colorRGB.toString() + "\n";
  }
}

class Circle extends Shape {
  private double radius;

  public Circle() {
    super();
    radius = 0.0D;
  }
  public Circle(double radius) {
    super();
    this.radius = radius;
  }
  public Circle(double radius, String colorLabel, ColorRGB colorRGB) {
    super(colorLabel, colorRGB);
    this.radius = radius;
  }
  public double getRadius() {
    return radius;
  }
  public void setRadius(double radius) {
    this.radius = radius;
  }

  @Override
  public double area() {
    return radius * radius * Math.PI;
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj)
           && obj instanceof Circle
           && radius == ((Circle) obj).getRadius();
  }
  @Override
  public String toString() {
    return super.toString() +
           "Radius = " + radius + "\n";
  }
}

class Triangle extends Shape {
  private double base;
  private double height;

  public Triangle() {
    super();
    this.base = 0.0D;
    this.height = 0.0D;
  }
  public Triangle(double base, double height) {
    super();
    this.base = base;
    this.height = height;
  }
  public Triangle(double base, double height, String colorLabel, ColorRGB colorRGB) {
    super(colorLabel, colorRGB);
    this.base = base;
    this.height = height;
  }
  public double getBase() {
    return base;
  }
  public double getHeight() {
    return height;
  }
  public void setBase(double base) {
    this.base = base;
  }
  public void setHeight(double height) {
    this.height = height;
  }
  @Override
  public double area() {
    return base * height / 2;
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj)
           && obj instanceof Triangle
           && base == ((Triangle) obj).getBase()
           && height == ((Triangle) obj).getHeight();
  }
  @Override
  public String toString() {
    return super.toString() +
           "Base = " + base + "\n" +
           "Height = " + height + "\n";
  }
}

class Rectangle extends Shape {
  private double length;
  private double width;

  public Rectangle() {
    super();
    this.length = 0.0D;
    this.width = 0.0D;
  }
  public Rectangle(double length, double width) {
    super();
    this.length = length;
    this.width = width;
  }
  public Rectangle(double length, double width, String colorLabel, ColorRGB colorRGB) {
    super(colorLabel, colorRGB);
    this.length = length;
    this.width = width;
  }
  public double getLength() {
    return length;
  }
  public double getWidth() {
    return width;
  }
  public void setLength(double length) {
    this.length = length;
  }
  public void setWidth(double width) {
    this.width = width;
  }
  
  @Override
  public double area() {
    return length * width;
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj)
           && obj instanceof Rectangle
           && length == ((Rectangle) obj).getLength()
           && width == ((Rectangle) obj).getWidth();
  }
  @Override
  public String toString() {
    return super.toString() +
           "Length = " + length + "\n" +
           "Width = " + width + "\n";
  }
}

class Rectangle3D extends Rectangle {
  private double height;

  public Rectangle3D() {
    super();
    height = 0.0D;
  }
  public Rectangle3D(double height) {
    super();
    this.height = height;
  }
  public Rectangle3D(double height, double length, double width, String colorLabel, ColorRGB colorRGB) {
    super(length, width, colorLabel, colorRGB);
    this.height = height;
  }
  public double getHeight() {
    return height;
  }
  public void setHeight(double height) {
    this.height = height;
  }

  @Override
  public double area() {
    double side1, side2, side3;

    side1 = super.area() * 2;
    side2 = height * getLength() * 2;
    side3 = height * getWidth() * 2;

    return side1 + side2 + side3;
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj)
           && obj instanceof Rectangle3D
           && height == ((Rectangle3D) obj).getHeight();
  }
  @Override
  public String toString() {
    return super.toString() +
           "Height = " + height + "\n";
  }
}

/*****************************************************************
 * Exercise:  Create classes representing Cylinder and Sphere.   *
 *            Create instances of all the class types            *
 *              and call the methods associated with each class. *
 *****************************************************************/

class Cylinder extends Circle {
  private double height;

  public Cylinder() {
    super();
    height = 0.0D;
  }
  public Cylinder(double height) {
    super();
    this.height = height;
  }
  public Cylinder(double height, double radius, String colorLabel, ColorRGB colorRGB) {
    super(radius, colorLabel, colorRGB);
    this.height = height;
  }
  public double getHeight() {
    return height;
  }
  public void setHeight(double height) {
    this.height = height;
  }

  @Override
  public double area() {
    double base, side;

    base = super.area() * 2;
    side = height * getRadius() * Math.PI * 2;

    return base + side;
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj)
           && obj instanceof Cylinder
           && height == ((Cylinder) obj).getHeight();
  }
  @Override
  public String toString() {
    return super.toString() +
           "Height = " + height + "\n";
  }
}

class Sphere extends Circle {
  public Sphere() {
    super();
  }
  public Sphere(double radius, String colorLabel, ColorRGB colorRGB) {
    super(radius, colorLabel, colorRGB);
  }

  @Override
  public double area() {
    return super.area() * 4;
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj)
           && obj instanceof Sphere;
  }
  @Override
  public String toString() {
    return super.toString();
  }
}

/******************************************
 * Exercise:  Compile and execute Driver1 *
 ******************************************/

/******************************************************************
 * Exercise:  Modify Shape so that it does not create a deep copy *
 *              of colorRGB, creating a shallow copy instead.     *
 *            Then, re-compile and execute Driver1.               *
 ******************************************************************/

class Driver1 {
  public static void main(String args[]) {
    ColorRGB rgb1 = new ColorRGB(255, 165, 0);
    Circle c1 = new Circle(5, "orange", rgb1);
    Circle c2 = new Circle(10, "orange", rgb1);

    System.out.println("***** C1 ==> Before *****");
    System.out.println(c1.toString());
    System.out.println("***** C2 ==> Before *****");
    System.out.println(c2.toString());

    c2.setColorLabel("blue");
    ColorRGB rgb2 = c2.getColorRGB();
    rgb2.setRed(0);
    rgb2.setGreen(0);
    rgb2.setBlue(255);

    System.out.println("***** C1 ==> After *****");
    System.out.println(c1.toString());
    System.out.println("***** C2 ==> After *****");
    System.out.println(c2.toString());

  }
}

/******************************************
 * Exercise:  Compile and execute Driver2 *
 ******************************************/

class Driver2 {
  public static void main(String args[]) {
    Rectangle3D rectangle3D = new Rectangle3D(2, 3, 1, "blue", new ColorRGB(0, 0, 255));

    System.out.println();
    System.out.println("The area of our Rectangle3D = " + rectangle3D.area());

    Rectangle rectangle = new Rectangle3D(2, 4, 8, "red", new ColorRGB(255, 0, 0));
    System.out.println();
    System.out.println("The area of rectangle variable\n" +
                       "storing a reference to a Rectangle3D = " + rectangle.area());
    System.out.println();
  }
}

/******************************************
 * Exercise:  Compile and execute Driver3 *
 ******************************************/

class Driver3 {
  public static void main(String args[]) {
    Circle c = new Circle(4, "blue", new ColorRGB(0, 0, 255));

    Rectangle r = new Rectangle(2, 5, "red", new ColorRGB(255, 0, 0));

    Triangle t = new Triangle(3, 6, "green", new ColorRGB(0, 255, 0));

    Rectangle3D r3d_1 = new Rectangle3D(4, 2, 3, "orange", new ColorRGB(255, 165, 0));

    Rectangle rectangle = r3d_1;
    System.out.println("? = " + rectangle.area());

    Rectangle3D r3d_2;
    if ( rectangle instanceof Rectangle3D ) {
      r3d_2 = (Rectangle3D) rectangle;
      System.out.println("? = " + r3d_2.area());
    }

    calculate(c);
    calculate(r);
    calculate(t);
  }
  public static void calculate(Shape shape) {
    System.out.println(shape.area());
  }
}

// Custom Driver4 class to test Cylinder and Sphere
class Driver4 {
  public static void main(String args[]) {
    Cylinder c = new Cylinder(1, 10, "pink", new ColorRGB(255, 109, 178));

    Sphere s = new Sphere(6, "violet", new ColorRGB(219, 189, 207));

    System.out.println(c.toString());
    System.out.println("The area of our Cylinder = " + c.area() + '\n');
    System.out.println(s.toString());
    System.out.println("The area of our Sphere  = " + s.area() + '\n');
  }
}