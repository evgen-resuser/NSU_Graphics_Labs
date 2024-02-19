package org.evgen.tools.fill;

import java.awt.*;

class Span {
    private final Point start;
    private final Point end;

     Span(Point start, Point end) {
         this.start = start;
         this.end = end;
     }

   public int intGetX1() {
         return start.x;
   }

    public int intGetX2() {
        return end.x;
    }

    public int intGetY1() {
        return start.y;
    }

    public int intGetY2() {
        return end.y;
    }
}
