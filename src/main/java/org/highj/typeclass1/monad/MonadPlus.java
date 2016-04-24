package org.highj.typeclass1.monad;

import org.highj._;
import org.highj.data.collection.List;
import org.highj.typeclass1.alternative.Alternative;

public interface MonadPlus<M> extends MonadZero<M>, Alternative<M> {

    //MonadPlus.(++) (Control.Monad)
    public <A> _<M, A> mplus(_<M, A> one, _<M, A> two);


    //msum (Control.Monad)
    public default <A> _<M, A> msum(_<List.µ, _<M, A>> list) {
        return List.narrow(list).foldr((_<M, A> one) -> (_<M, A> two) -> mplus(one, two), this.<A>mzero());
    }

    enum Bias {
        FIRST {
            @Override
            public <T> T select(T first, T last) {
                return first;
            }
        }, LAST {
            @Override
            public <T> T select(T first, T last) {
                return last;
            }
        };

        public abstract <T> T select(T first, T last);
    }
}
