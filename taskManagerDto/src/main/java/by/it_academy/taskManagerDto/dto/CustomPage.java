package by.it_academy.taskManagerDto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"number", "size", "totalPages", "totalElements", "first", "numberOfElements", "last", "content"})
public class CustomPage<K> {

    private Integer number;

    private Integer size;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("total_elements")
    private Long totalElements;

    private boolean first;

    @JsonProperty("number_of_elements")
    private Integer numberOfElements;

    private boolean last;

    private List<K> content;

    public CustomPage() {
    }

    public CustomPage(Integer number, Integer size, Integer totalPages, Long totalElements, boolean first, Integer numberOfElements, boolean last, List<K> content) {
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.first = first;
        this.numberOfElements = numberOfElements;
        this.last = last;
        this.content = content;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean getFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean getLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public List<K> getContent() {
        return content;
    }

    public void setContent(List<K> content) {
        this.content = content;
    }
}
