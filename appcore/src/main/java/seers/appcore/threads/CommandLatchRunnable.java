package seers.appcore.threads;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import seers.appcore.threads.processor.ThreadProcessor;

public class CommandLatchRunnable implements Runnable {

	private static Logger LOGGER = LoggerFactory.getLogger(CommandLatchRunnable.class);

	private CountDownLatch cntDwnLatch;
	private ThreadProcessor proc;

	public CommandLatchRunnable(ThreadProcessor proc) {
		this.proc = proc;
	}

	public CommandLatchRunnable(ThreadProcessor proc, CountDownLatch cntDwnLatch) {
		this.cntDwnLatch = cntDwnLatch;
		this.proc = proc;
	}

	@Override
	public void run() {
		try {
			proc.processJob();
		} catch (Exception e) {
			LOGGER.error(proc.getName() + ": ", e);
		} finally {
			if (cntDwnLatch != null) {
				cntDwnLatch.countDown();
			}
		}

	}

	public String getName() {
		return proc.getName();
	}

}