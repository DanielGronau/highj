/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.highj.typeclass2.arrow;

import org.highj.__;
import org.highj.___;

/**
 *
 * @author clintonselke
 */
public interface ArrowTransformer<F,A> extends Arrow<___.µ<F,A>> {
    
    public <B,C> ___<F,A,B,C> lift(__<A,B,C> arrow);
}
