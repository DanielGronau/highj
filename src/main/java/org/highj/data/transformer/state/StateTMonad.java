package org.highj.data.transformer.state;

import org.highj._;
import org.highj.__;
import org.highj.___;
import org.highj.data.transformer.StateT;
import org.highj.typeclass1.monad.Monad;

/**
 * @author Clinton Selke
 */
public interface StateTMonad<S, M> extends StateTApplicative<S, M>, StateTBind<S, M>, Monad<_<_<StateT.µ, S>, M>> {

}