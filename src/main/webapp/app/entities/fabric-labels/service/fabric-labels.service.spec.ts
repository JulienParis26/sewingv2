import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFabricLabels } from '../fabric-labels.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fabric-labels.test-samples';

import { FabricLabelsService } from './fabric-labels.service';

const requireRestSample: IFabricLabels = {
  ...sampleWithRequiredData,
};

describe('FabricLabels Service', () => {
  let service: FabricLabelsService;
  let httpMock: HttpTestingController;
  let expectedResult: IFabricLabels | IFabricLabels[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FabricLabelsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FabricLabels', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fabricLabels = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fabricLabels).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FabricLabels', () => {
      const fabricLabels = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fabricLabels).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FabricLabels', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FabricLabels', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FabricLabels', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFabricLabelsToCollectionIfMissing', () => {
      it('should add a FabricLabels to an empty array', () => {
        const fabricLabels: IFabricLabels = sampleWithRequiredData;
        expectedResult = service.addFabricLabelsToCollectionIfMissing([], fabricLabels);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricLabels);
      });

      it('should not add a FabricLabels to an array that contains it', () => {
        const fabricLabels: IFabricLabels = sampleWithRequiredData;
        const fabricLabelsCollection: IFabricLabels[] = [
          {
            ...fabricLabels,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFabricLabelsToCollectionIfMissing(fabricLabelsCollection, fabricLabels);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FabricLabels to an array that doesn't contain it", () => {
        const fabricLabels: IFabricLabels = sampleWithRequiredData;
        const fabricLabelsCollection: IFabricLabels[] = [sampleWithPartialData];
        expectedResult = service.addFabricLabelsToCollectionIfMissing(fabricLabelsCollection, fabricLabels);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricLabels);
      });

      it('should add only unique FabricLabels to an array', () => {
        const fabricLabelsArray: IFabricLabels[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fabricLabelsCollection: IFabricLabels[] = [sampleWithRequiredData];
        expectedResult = service.addFabricLabelsToCollectionIfMissing(fabricLabelsCollection, ...fabricLabelsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fabricLabels: IFabricLabels = sampleWithRequiredData;
        const fabricLabels2: IFabricLabels = sampleWithPartialData;
        expectedResult = service.addFabricLabelsToCollectionIfMissing([], fabricLabels, fabricLabels2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fabricLabels);
        expect(expectedResult).toContain(fabricLabels2);
      });

      it('should accept null and undefined values', () => {
        const fabricLabels: IFabricLabels = sampleWithRequiredData;
        expectedResult = service.addFabricLabelsToCollectionIfMissing([], null, fabricLabels, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fabricLabels);
      });

      it('should return initial array if no FabricLabels is added', () => {
        const fabricLabelsCollection: IFabricLabels[] = [sampleWithRequiredData];
        expectedResult = service.addFabricLabelsToCollectionIfMissing(fabricLabelsCollection, undefined, null);
        expect(expectedResult).toEqual(fabricLabelsCollection);
      });
    });

    describe('compareFabricLabels', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFabricLabels(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFabricLabels(entity1, entity2);
        const compareResult2 = service.compareFabricLabels(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFabricLabels(entity1, entity2);
        const compareResult2 = service.compareFabricLabels(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFabricLabels(entity1, entity2);
        const compareResult2 = service.compareFabricLabels(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
