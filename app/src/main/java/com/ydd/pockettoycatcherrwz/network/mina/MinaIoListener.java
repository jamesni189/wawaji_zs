package com.ydd.pockettoycatcherrwz.network.mina;

import com.ydd.pockettoycatcherrwz.util.LogUtil;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * io服务监听器
 * 
 * Created by czhang on 17/1/18.
 */

public class MinaIoListener implements IoServiceListener {
	/**
	 * serviceActivated(IoService)
	 * 当service生效时被调用。当新的service被bind时或第一个session生成时，IoServiceListenerSupport.
	 * fireServiceActivated被调用同时调用此方法。
	 *
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void serviceActivated(IoService session) throws Exception {
		LogUtil.printMina("serviceActivated");
	}

	/**
	 * serviceDeactivated(IoService)
	 * 当service失效时被调用。当service被unbind或最后个session注销时，IoServiceListenerSupport.
	 * fireServiceDeactivated被调用同时调用此方法。
	 *
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void serviceDeactivated(IoService session) throws Exception {
		LogUtil.printMina("serviceDeactivated");
	}

	/**
	 * serviceIdle(IoService, IdleStatus) 当service空闲时被调用，不过此方法没有在mina中真正使用。
	 *
	 * @param session
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void serviceIdle(IoService session, IdleStatus status)
			throws Exception {
		LogUtil.printMina("serviceIdle");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		LogUtil.printMina("sessionClosed");
	}

	/**
	 * sessionCreated(IoSession)
	 * 当新的session生成时被调用。IoServiceListenerSupport.fireSessionCreated
	 *
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		LogUtil.printMina("sessionCreated");
	}

	/**
	 * sessionDestroyed(IoSession)
	 * 当session被注销时调用。IoServiceListenerSupport.fireSessionDestroyed
	 *
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void sessionDestroyed(IoSession session) throws Exception {
		LogUtil.printMina("sessionDestroyed");
	}

}
