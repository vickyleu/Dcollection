package com.benny.library.kbinding.adapterview.bindings.utils

import android.widget.ListView
import com.benny.library.autoadapter.listener.AdapterPagingListener
import com.benny.library.kbinding.adapterview.bindings.setPagingListener
import com.jakewharton.rxbinding.internal.MainThreadSubscription
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import org.reactivestreams.Subscriber
import rx.Observable
import rx.Subscriber

/**
 * Created by benny on 12/26/15.
 */

class ListViewPagingOnSubscribe(val view: ListView) : ObservableOnSubscribe<Pair<Int, Any?>> {
    /**
     * Called for each Observer that subscribes.
     * @param e the safe emitter instance, never null
     * @throws Exception on error
     */
    override fun subscribe(e: ObservableEmitter<Pair<Int, Any?>>) {
        val pagingListener = AdapterPagingListener<Any?> { receiver, previous, position ->
            e.isDisposed
            if (e.isUnsubscribed) return@AdapterPagingListener

            e.onNext(Pair(position, previous));
        }
        view.setPagingListener(pagingListener)
        e.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                view.setPagingListener(null)
            }
        })
    }
    override fun call(subscriber: Subscriber<in Pair<Int, Any?>>) {
        val pagingListener = AdapterPagingListener<Any?> { receiver, previous, position ->

            if (subscriber.isUnsubscribed) return@AdapterPagingListener

            subscriber.onNext(Pair(position, previous));
        }
        view.setPagingListener(pagingListener)
        subscriber.add(object : MainThreadSubscription() {
            override fun onUnsubscribe() {
                view.setPagingListener(null)
            }
        })
    }
}