package com.example.grpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import Inquiry
import APIServiceGrpc

class GrpcClient(
    private val host: String = "go.signorder.kr",
    private val port: Int = 443
) {
    private val channel: ManagedChannel = ManagedChannelBuilder
        .forAddress(host, port)
        .useTransportSecurity()
        .build()

    private val stub: APIServiceGrpc.APIServiceStub = APIServiceGrpc.newStub(channel)

    // 영상 미디어파이프 결과 전송
    fun sendAllFrameData(
        frameData: List<Float>,
        inquiryType: String = "order",
        num: Int = 123,
        onLog: (String) -> Unit
    ) {
        val responseObserver = object : StreamObserver<Inquiry.InquiryResponse> {
            override fun onNext(value: Inquiry.InquiryResponse) {
                onLog("응답: ${value.success}")
            }

            override fun onError(t: Throwable) {
                onLog("오류 발생: ${t.message}")
                onLog("StackTrace: ${t.stackTraceToString()}")
            }

            override fun onCompleted() {
                onLog("영상 미디어파이프 결과 전송 완료")
            }
        }

        val requestObserver = stub.streamInquiries(responseObserver)

        val request = Inquiry.InquiryRequest.newBuilder()
            .setStoreCode("5fjVwE8z")
            .setInquiryType(inquiryType)
            .setNum(num)
            .addAllFrameData(frameData)
            .build()

        requestObserver.onNext(request)
        requestObserver.onCompleted()
    }


    // '문의사항 있으십니까?' X 버튼 클릭 시
    fun sendFastInquiry(
        title: String,
        num: Int,
        onResult: (Boolean) -> Unit
    ) {
        val request = Inquiry.FastInquiryRespIsNoRequest.newBuilder()
            .setStoreCode("5fjVwE8z")
            .setTitle(title)
            .setNum(num)
            .build()

        stub.fastInquiryRespIsNo(request, object : StreamObserver<Inquiry.FastInquiryRespIsNoResponse> {
            override fun onNext(value: Inquiry.FastInquiryRespIsNoResponse) {
                println("FastInquiry 응답: ${value.success}")
                onResult(value.success)
            }

            override fun onError(t: Throwable) {
                t.printStackTrace()
                onResult(false)
            }

            override fun onCompleted() {
                println("FastInquiry 호출 완료")
            }
        })
    }

    fun shutdown() {
        channel.shutdown()
    }
}
