package org.tain.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

	@Bean(name = "async_errorReaderJob")
	public Executor errorMainTask() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(0);
		executor.setThreadNamePrefix("async_errorReaderJob-");
		executor.initialize();
		return executor;
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	@Bean(name = "task01Task")
	public Executor task01Task() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setQueueCapacity(0);
		executor.setThreadNamePrefix("task01Task-");
		executor.initialize();
		return executor;
	}
	*/
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/*
	 * CorePoolSize: 기본 쓰레드 사이즈
	 * MaxPoolSize: 최대 쓰레드 사이즈
	 * QueueCapacity: Max 쓰레드가 동작하는 경우 대기하는 queue 사이즈.
	 * 
	 * 최초 5번의 요청은 CorePoolSize에 설정한 쓰레드에 할당되고 쓰레드가 끝나기 전에 추가 요청이 들어오면
	 * MaxPoolSize에 설정한 시이즈 만큼 추가로 쓰레드가 생성되어 할당됩니다.
	 * 10개의 쓰레드가 모두 실행되고 있는 도중에 추가 요청이 들어오면 QueueCapacity에서 설정한 사이즈 만큼
	 * 대기열에서 기다리고 있습니다. 실행되고 있는 쓰레드가 종료되면 순차적으로 처리됩니다.
	 */
	/*
	@Bean(name = "fooExecutor")
	public Executor fooExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(30);
		executor.setThreadNamePrefix("fooExecutor-");
		executor.initialize();
		return executor;
	}
	*/

	/*
	 * 3개의 고정 크기로 3개의 쓰레드가 모두 처리중일때 추가 요청이 들어오면 4번째 요청은 Exception을 발생시킵니다.
	 */
	/*
	@Bean(name = "fooExecutor2")
	public Executor fooExecutor2() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(0);
		executor.setThreadNamePrefix("fooExecutor2-");
		executor.initialize();
		return executor;
	}
	*/
}