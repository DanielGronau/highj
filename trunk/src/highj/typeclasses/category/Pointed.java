/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj.typeclasses.category;

import highj._;
/**
 *
 * @author DGronau
 */
public interface Pointed<Ctor> extends PointedBounded<Ctor, Object>, Functor<Ctor> {
    // pure (Data.Pointed)
    
    @Override
    public <A> _<Ctor, A> pure(A a); 
    
    
}