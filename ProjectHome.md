You can [run slide rule as an applet](http://java-slide-rule.googlecode.com/svn/trunk/slide-rule/ant-build/sliderule.html), or [download a zip release](http://java-slide-rule.googlecode.com/svn/trunk/slide-rule/ant-build/sliderule.zip)





Availabre scales are:

**`K A [B S T ST CT CI CF C] D DF L R1 R2 LL0 LL1 LL2 LL3`**


  * **K** - The K scale allows finding cubes and cube roots, when used in conjunction with C or D. x<sup>3</sup>, <sup>3</sup>√x.
  * **A,B** - two-decade logarithmic scales.
  * **S** - A decimal trig scale with angles for sines 6º-90º running in one direction, and angles for cosines 84º - 0º running the other way. sin(x), cos(x), arcsin(x) and arccos(x).
  * **T** - A decimal trig scale with angles for tangents to 6º-45º running in one direction for use with the C scale, and angles for cotangents 45º-84º running the other way and corresponding to tangents on the CI scale.
  * **ST** - A decimal trig scale with angles for sines or tangents 0.6º-6º.
  * **CT** - angles for cosines or cotangents 89.4º - 84º.
  * **CI** - inverted C scale, for reciprocals: 1/x.
  * **C,D** - single-decade logarithmic scales.
  * **CF,DF** - folded versions of the C and D scales.
  * **L** - a linear scale, used along with the C and D scales for finding log<sub>10</sub>x and 10<sup>x</sup>.
  * **[R1](https://code.google.com/p/java-slide-rule/source/detail?r=1),[R2](https://code.google.com/p/java-slide-rule/source/detail?r=2)** - scale of square roots, allowing greater precision for √x and √10x
  * **LL0..3** - a set of log-log scales, used for finding log<sub>x</sub>y and x<sup>y</sup>.





![http://java-slide-rule.googlecode.com/svn/trunk/slide-rule/src/test/resources/slide-rule.png](http://java-slide-rule.googlecode.com/svn/trunk/slide-rule/src/test/resources/slide-rule.png)





The [zip release](http://java-slide-rule.googlecode.com/svn/trunk/slide-rule/ant-build/sliderule.zip) contains:

  * martinson-slide-rule.jar
  * sliderule.bat
  * sliderule.sh
  * sliderule.html (java applet)