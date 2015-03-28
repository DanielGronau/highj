package org.highj.data.transformer.reader;

import org.highj._;
import org.highj.___;
import org.highj.data.transformer.ReaderT;
import org.highj.typeclass1.monad.Monad;
import org.highj.typeclass1.monad.MonadTrans;

/**
 * @author Clinton Selke
 */
public interface ReaderTMonadTrans<R, M> extends ReaderTMonad<R, M>, MonadTrans<___.µ<ReaderT.µ, R>, M> {

    @Override
    public Monad<M> get();

    @Override
    public default <A> ReaderT<R, M, A> lift(_<M, A> nestedA) {
        return (R r) -> nestedA;
    }
}